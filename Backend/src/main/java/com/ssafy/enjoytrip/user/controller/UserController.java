package com.ssafy.enjoytrip.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.user.model.UserDto;
import com.ssafy.enjoytrip.user.model.service.UserService;
import com.ssafy.enjoytrip.utility.JWTUtility;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	private UserService userService;
	private JWTUtility jwtUtility;

	public UserController(UserService userService, JWTUtility jwtUtility) {
		super();
		this.userService = userService;
		this.jwtUtility = jwtUtility;
	}

	@ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) UserDto userDto) {
		log.debug("login user : {}", userDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			UserDto loginUser = userService.login(userDto);
			if (loginUser != null) {
				String accessToken = jwtUtility.createAccessToken(loginUser.getUserId());
				String refreshToken = jwtUtility.createRefreshToken(loginUser.getUserId());
				log.debug("access token : {}", accessToken);
				log.debug("refresh token : {}", refreshToken);

//				발급받은 refresh token을 DB에 저장.
				userService.saveRefreshToken(loginUser.getUserId(), refreshToken);

//				JSON으로 token 전달.
				resultMap.put("access-token", accessToken);

//				Cookie로 변경
				resultMap.put("refresh-token", refreshToken);

				status = HttpStatus.CREATED;
			} else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
			}

		} catch (Exception e) {
			log.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

//	header를 통해서 access token이 넘어오고, path variable을 통해서 userId가 넘어옴 (두 개)
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
//		세션을 사용하지 않기 때문에 header 안에서 vue에서 보냈던 token을 받아서 진위성 검증을 해야 함
//		checkToken은 이 token이 사용할 수 있는 토큰인지 여부를 반환함
		if (jwtUtility.checkToken(request.getHeader("Authorization"))) {
			log.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				UserDto userDto = userService.userInfo(userId);
				resultMap.put("userInfo", userDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "마이페이지 정보수정", notes = "수정할 마이페이지 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/info")
	public ResponseEntity<String> modifyUserInfo(
			@RequestBody @ApiParam(value = "수정할 회원정보.", required = true) Map<String, String> map) throws Exception {
		log.info("modifyUserInfo - 호출");
		userService.modifyUserInfo(map);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(
			@PathVariable("userId") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			log.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원가입", notes = "회원 정보를 입력한다.")
	@PostMapping("/info")
	public ResponseEntity<?> registerUser(
			@RequestBody @ApiParam(value = "회원 정보.", required = true) Map<String, String> map) throws Exception {
		log.info("registerUser - 호출", map);
		userService.registerUser(map);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		log.debug("token : {}, userDto : {}", token, userDto);
//		이걸 통과하면 토큰 자체에는 문제가 없음
		if (jwtUtility.checkToken(token)) {
//			그런데 이게 탈취한 토큰인지 아닌지 확인하기 위해서 userId에 해당하는 refreshToken과 같은지 체크함
			if (token.equals(userService.getRefreshToken(userDto.getUserId()))) {
//				다시 accessToken 발급해줌
				String accessToken = jwtUtility.createAccessToken(userDto.getUserId());
				log.debug("token : {}", accessToken);
				log.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			log.debug("리프레쉬토큰도 사용불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
