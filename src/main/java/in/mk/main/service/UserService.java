package in.mk.main.service;

import in.mk.main.dto.PasswordChangeRequest;
import in.mk.main.dto.PswdResetRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

public void changePassword(PasswordChangeRequest passwordRequest);

public void sendEmailPasswordReset(String email,HttpServletRequest request) throws Exception;

public void verifypswdResetLink(Integer uid, String code) throws Exception;

public void resetPassword(PswdResetRequest pswdResetRequest) throws Exception;
}
