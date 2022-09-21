package il.ac.hit.jfxclothesshop.startup;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//setup users manager
@Builder
@NoArgsConstructor
@Data
public class Setup {
    private String managerUser;
    private String managerPassword;
    private String managerPhone;
    private String managerIdPerson;
    private String managerName;
    private String managerAccountNumber;
    private String managerBranch;
    private String managerType;

//this code will help to read the setup.jason file
    @JsonCreator
    public Setup(@JsonProperty("managerUser") String managerUser,
                 @JsonProperty("managerPassword") String managerPassword,
                 @JsonProperty("managerName") String managerName,
                 @JsonProperty("managerIdPerson") String managerIdPerson,
                 @JsonProperty("managerPhone") String managerPhone,
                 @JsonProperty("managerAccountNumber") String managerAccountNumber,
                 @JsonProperty("managerBranch") String managerBranch,
                 @JsonProperty("managerType") String managerType){
        this.managerUser = managerUser;
        this.managerPassword = managerPassword;
        this.managerName=managerName;
        this.managerIdPerson=managerIdPerson;
        this.managerPhone=managerPhone;
        this.managerAccountNumber=managerAccountNumber;
        this.managerBranch=managerBranch;
        this.managerType=managerType;
    }
}
