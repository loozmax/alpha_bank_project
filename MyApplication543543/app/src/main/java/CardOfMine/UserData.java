package CardOfMine;

public class UserData {

    String id, userName, userLastName, userOtchestvo, userAppeal, userOrganisation, userPhone, userEmail, userVK, userFB;

    public UserData(String id, String userName, String userLastName, String userOtchestvo, String userAppeal, String userOrganisation, String userPhone, String userEmail, String userVK, String userFB) {
        this.id = id;
        this.userName = userName;
        this.userLastName = userLastName;
        this.userOtchestvo = userOtchestvo;
        this.userAppeal = userAppeal;
        this.userOrganisation = userOrganisation;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userVK = userVK;
        this.userFB = userFB;
    }

    public UserData() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserOtchestvo() {
        return userOtchestvo;
    }

    public void setUserOtchestvo(String userOtchestvo) {
        this.userOtchestvo = userOtchestvo;
    }

    public String getUserAppeal() {
        return userAppeal;
    }

    public void setUserAppeal(String userAppeal) {
        this.userAppeal = userAppeal;
    }

    public String getUserOrganisation() {
        return userOrganisation;
    }

    public void setUserOrganisation(String userOrganisation) {
        this.userOrganisation = userOrganisation;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserVK() {
        return userVK;
    }

    public void setUserVK(String userVK) {
        this.userVK = userVK;
    }

    public String getUserFB() {
        return userFB;
    }

    public void setUserFB(String userFB) {
        this.userFB = userFB;
    }
}
