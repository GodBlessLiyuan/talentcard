package sbsdk.talentcard.bsnsdk.util.common;

import sbsdk.hyperledger.fabric.protos.peer.ProposalPackage;
import sbsdk.talentcard.bsnsdk.entity.config.Config;
import sbsdk.talentcard.bsnsdk.entity.transactionHeader.TransactionHeader;
import sbsdk.talentcard.bsnsdk.entity.transactionHeader.TransactionRequest;
import sbsdk.talentcard.bsnsdk.entity.transactionHeader.TransactionUser;
import sbsdk.talentcard.bsnsdk.util.enums.AlgorithmTypeEnum;
import sbsdk.talentcard.bsnsdk.util.enums.ResultInfoEnum;
import sbsdk.talentcard.bsnsdk.util.exception.GlobalException;
import sbsdk.talentcard.bsnsdk.util.trans.FabricTransUtil;
import com.google.protobuf.util.Timestamps;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.jetbrains.annotations.NotNull;
import sbsdk.hyperledger.fabric.protos.common.Common;
import sbsdk.hyperledger.fabric.protos.msp.Identities;
import sbsdk.hyperledger.fabric.protos.peer.Chaincode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class TransData {
    private static sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeInput chaincodeInput(@NotNull String funcName, byte[][] args) {

        sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeInput.Builder ct = sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeInput.newBuilder();
        ct.addArgs(com.google.protobuf.ByteString.copyFrom(funcName.getBytes()));
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                ct.addArgs(com.google.protobuf.ByteString.copyFrom(args[i]));
            }
        }
        return ct.build();
    }

    private static sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeID chaincodeID(String name) {
        sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeID.Builder ct = sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeID.newBuilder();
        ct.setName(name);
        return ct.build();
    }

    private static ProposalPackage.ChaincodeProposalPayload chaincodeProposalPayload(sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeInput input, sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeID chaincodeId, Map<String, String> map) throws IOException {
        sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeSpec.Builder spec = sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeSpec.newBuilder();
        spec.setType(sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeSpec.Type.JAVA);
        spec.setInput(input);
        spec.setChaincodeId(chaincodeId);

        sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeInvocationSpec.Builder chaincodeProposalPayload = sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeInvocationSpec.newBuilder();
        chaincodeProposalPayload.setChaincodeSpec(spec);

        ProposalPackage.ChaincodeProposalPayload.Builder ChaincodeProposalPayload = ProposalPackage.ChaincodeProposalPayload.newBuilder();
        ChaincodeProposalPayload.setInput(convertToByteString(chaincodeProposalPayload.build()));
        if (map != null) {
            for (String key : map.keySet()) {
                ChaincodeProposalPayload.putTransientMap(key, getByteString(map.get(key).getBytes()));
            }
        }
        return ChaincodeProposalPayload.build();
    }

    private static sbsdk.hyperledger.fabric.protos.msp.Identities.SerializedIdentity serializedIdentity(String mspid, byte[] cert) {
        sbsdk.hyperledger.fabric.protos.msp.Identities.SerializedIdentity.Builder serializedIdentity = sbsdk.hyperledger.fabric.protos.msp.Identities.SerializedIdentity.newBuilder();
        serializedIdentity.setMspid(mspid);
        serializedIdentity.setIdBytes(getByteString(cert));
        return serializedIdentity.build();
    }

    private static TransactionHeader transactionHeader(String channeId, String mspId, byte[] cert) throws IOException {
        TransactionHeader transactionHeader = new TransactionHeader();
        transactionHeader.setChannelId(channeId);
        byte[] nonce = Nonce.getNonce();
        transactionHeader.setNonce(getByteString(nonce));
        Identities.SerializedIdentity creator = serializedIdentity(mspId, cert);
        byte[] creatorBytes = convertToBytes(creator);
        transactionHeader.setCreator(convertToByteString(creator));
        transactionHeader.setTransactionId(getTxid(nonce, creatorBytes));
        return transactionHeader;
    }

    private static sbsdk.hyperledger.fabric.protos.common.Common.Header header(@NotNull TransactionHeader tx, sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeID chaincode) throws IOException {

        ProposalPackage.ChaincodeHeaderExtension.Builder ext = ProposalPackage.ChaincodeHeaderExtension.newBuilder();
        ext.setChaincodeId(chaincode);

        sbsdk.hyperledger.fabric.protos.common.Common.ChannelHeader.Builder channelHeader = sbsdk.hyperledger.fabric.protos.common.Common.ChannelHeader.newBuilder();
        channelHeader.setType(sbsdk.hyperledger.fabric.protos.common.Common.HeaderType.ENDORSER_TRANSACTION_VALUE);

        channelHeader.setTxId(tx.getTransactionId());
        channelHeader.setChannelId(tx.getChannelId());
        channelHeader.setExtension(convertToByteString(ext.build()));
        channelHeader.setEpoch(0);

        com.google.protobuf.Timestamp ts = Timestamps.fromMillis(new Date().getTime());
        channelHeader.setTimestamp(ts);

        sbsdk.hyperledger.fabric.protos.common.Common.SignatureHeader.Builder signHeader = sbsdk.hyperledger.fabric.protos.common.Common.SignatureHeader.newBuilder();
        signHeader.setNonce(tx.getNonce());
        signHeader.setCreator(tx.getCreator());


        sbsdk.hyperledger.fabric.protos.common.Common.Header.Builder header = sbsdk.hyperledger.fabric.protos.common.Common.Header.newBuilder();
        header.setChannelHeader(convertToByteString(channelHeader.build()));
        header.setSignatureHeader(convertToByteString(signHeader.build()));

        return header.build();
    }


    private static ProposalPackage.Proposal proposal(@NotNull TransactionRequest request, @NotNull TransactionUser user) throws IOException {
        sbsdk.hyperledger.fabric.protos.peer.Chaincode.ChaincodeInput input = chaincodeInput(request.getFcn(), request.getArgs());
        Chaincode.ChaincodeID chaincodeId = chaincodeID(request.getChaincodeId());
        ProposalPackage.ChaincodeProposalPayload payload = chaincodeProposalPayload(input, chaincodeId, request.getTransientMap());
        TransactionHeader transactionHeader = transactionHeader(request.getChanelId(), user.getMspId(), user.getCert());
        Common.Header header = header(transactionHeader, chaincodeId);
        ProposalPackage.Proposal.Builder proposal = ProposalPackage.Proposal.newBuilder();
        proposal.setHeader(convertToByteString(header));
        proposal.setPayload(convertToByteString(payload));
        return proposal.build();

    }

    public static String getTransdata(TransactionRequest request, TransactionUser user) throws Exception {
        ProposalPackage.Proposal proposal = proposal(request, user);
        byte[] proposalBytes = convertToBytes(proposal);
        byte[] signBytes;
        try {
            AlgorithmTypeEnum algorithmTypeEnum = AlgorithmTypeEnum.fromAlgorithmTypeEnum(Config.config.getAppInfo().getAlgorithmType());
            signBytes = FabricTransUtil.getTransSign(algorithmTypeEnum,user.getPrivateKey(),proposalBytes);
        } catch (CryptoException e) {
            e.printStackTrace();
            throw new GlobalException(ResultInfoEnum.TRANSACTION_SIGNING_ERROR);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new GlobalException(ResultInfoEnum.TRANSACTION_SIGNING_ERROR);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new GlobalException(ResultInfoEnum.TRANSACTION_SIGNING_ERROR);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new GlobalException(ResultInfoEnum.TRANSACTION_SIGNING_ERROR);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            throw new GlobalException(ResultInfoEnum.TRANSACTION_SIGNING_ERROR);
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
            throw new GlobalException(ResultInfoEnum.TRANSACTION_SIGNING_ERROR);
        } catch (KeyStoreException e) {
            e.printStackTrace();
            throw new GlobalException(ResultInfoEnum.TRANSACTION_SIGNING_ERROR);
        }
        ProposalPackage.SignedProposal.Builder sig = ProposalPackage.SignedProposal.newBuilder();
        sig.setProposalBytes(getByteString(proposalBytes));
        sig.setSignature(getByteString(signBytes));
        byte[] signProposalBytes = convertToBytes(sig.build());
        return Base64.getEncoder().encodeToString(signProposalBytes);
    }


    private static String getTxid(@NotNull byte[] nonce, @NotNull byte[] creator) {
        byte[] by = new byte[nonce.length + creator.length];
        System.arraycopy(nonce, 0, by, 0, nonce.length);
        System.arraycopy(creator, 0, by, nonce.length, creator.length);
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(by);
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    @NotNull
    private static String byte2Hex(@NotNull byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    private static byte[] convertToBytes(@NotNull com.google.protobuf.GeneratedMessageV3 msg) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        com.google.protobuf.CodedOutputStream stream = com.google.protobuf.CodedOutputStream.newInstance(bout);
        msg.writeTo(stream);
        stream.flush();
        byte[] bytes = bout.toByteArray();
        bout.close();
        return bytes;

    }

    private static com.google.protobuf.ByteString convertToByteString(@NotNull com.google.protobuf.GeneratedMessageV3 msg) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        com.google.protobuf.CodedOutputStream stream = com.google.protobuf.CodedOutputStream.newInstance(bout);
        msg.writeTo(stream);
        stream.flush();
        com.google.protobuf.ByteString str = getByteString(bout.toByteArray());
        bout.close();
        return str;

    }

    @NotNull
    private static com.google.protobuf.ByteString getByteString(byte[] bytes) {
        return com.google.protobuf.ByteString.copyFrom(bytes);
    }


}
