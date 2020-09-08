package sbsdk.convert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-09-08 16:00
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TransactionStatus {
    OK("OK"),
    Error("Error");
    private String status;
}
