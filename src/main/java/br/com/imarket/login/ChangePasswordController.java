package br.com.imarket.login;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.composed.web.rest.json.PostJson;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.imarket.exception.PasswordTokenNotFoundException;

@RestController
public class ChangePasswordController {
	
	@Autowired
	private LoginInfoRepository loginInfoRepository;
	@Autowired
	private PasswordTokenRepository passwordTokenRepository;

	@PostJson("/change-password")
	public void index(@Valid @RequestBody ChangePasswordDTO dto) {
		LoginInfo loginInfo = loginInfoRepository.findById(dto.getId()).orElseThrow(LoginInfoNotFoundException::new);
		PasswordToken passwordToken = passwordTokenRepository.findByLoginInfoAndToken(loginInfo, dto.getToken())
						.orElseThrow(PasswordTokenNotFoundException::new);
		
		loginInfo.setPassword(dto.getPassword());
		loginInfoRepository.save(loginInfo);
		passwordTokenRepository.delete(passwordToken);
	}
}
