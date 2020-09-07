一开始的时候报错：No MyBatis mapper was found in 'com.talentcard.bsnsdk':
我在maven 依赖 看到mybatis的jar包之后，在配置文件中配置了数据库，之后启动还是报错，我引入公共common-lib之后，不报错。

我首先移动sdk内容，发现存在依赖，然后移入 chaincodeEntities 内容，最后移动了 org.hyperledger.fabric.protos内容，
改动了包名，将org 改为com，但是这样改则改动很多，比较麻烦。
我采用将com重命名为org方式，移动源 hyperledger.fabric.protos 文件之后：部分文件缺包：
在 org.talentcard.bsnsdk.util.common.TransData导入import org.hyperledger.fabric.protos.common.Common;
在 org.talentcard.bsnsdk.util.sm2.SM2SignVerUtils 导入 import org.bouncycastle.asn1.*;
之后将org包重命名了一下，改为sbsdk，期间进程很慢。
Correct the classpath of your application so that it contains a single, compatible version of com.gson 删除该jar包一切正常

feign调用配置
首先引入maven、做好配置，使用controller调用对应的service、做好参数和返回值
写好controller的接口，使用feine注解并实现该接口，springboot自动注入feign类并可以当做对象使用


