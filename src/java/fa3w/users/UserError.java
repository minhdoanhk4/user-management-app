/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa3w.users;

/**
 *
 * @author msi2k
 */
public class UserError {
    private String userIDError;
    private String fullNameError;
    private String roleIDError;
    private String passwordError;
    private String confirmdError;
    private String error;

    public UserError() {
        this.userIDError = "";
        this.fullNameError = "";
        this.roleIDError = "";
        this.passwordError = "";
        this.confirmdError = "";
        this.error = "";
    }

    public UserError(String userIDError, String fullNameError, String roleIDError, String passwordError, String confirmdError, String error) {
        this.userIDError = userIDError;
        this.fullNameError = fullNameError;
        this.roleIDError = roleIDError;
        this.passwordError = passwordError;
        this.confirmdError = confirmdError;
        this.error = error;
    }
    
    

    /**
     * @return the userIDError
     */
    public String getUserIDError() {
        return userIDError;
    }

    /**
     * @param userIDError the userIDError to set
     */
    public void setUserIDError(String userIDError) {
        this.userIDError = userIDError;
    }

    /**
     * @return the fullNameError
     */
    public String getFullNameError() {
        return fullNameError;
    }

    /**
     * @param fullNameError the fullNameError to set
     */
    public void setFullNameError(String fullNameError) {
        this.fullNameError = fullNameError;
    }

    /**
     * @return the roleIDError
     */
    public String getRoleIDError() {
        return roleIDError;
    }

    /**
     * @param roleIDError the roleIDError to set
     */
    public void setRoleIDError(String roleIDError) {
        this.roleIDError = roleIDError;
    }

    /**
     * @return the passwordError
     */
    public String getPasswordError() {
        return passwordError;
    }

    /**
     * @param passwordError the passwordError to set
     */
    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    /**
     * @return the confirmdError
     */
    public String getConfirmdError() {
        return confirmdError;
    }

    /**
     * @param confirmdError the confirmdError to set
     */
    public void setConfirmdError(String confirmdError) {
        this.confirmdError = confirmdError;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }
    
    
    
}
