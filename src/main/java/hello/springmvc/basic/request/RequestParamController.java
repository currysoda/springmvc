package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Slf4j
@Controller
public class RequestParamController {

	/**
	 * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
	 */
	@RequestMapping("/request-param-v1")
	public void requestParamV1(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String username = req.getParameter("username");
		int age = Integer.parseInt(req.getParameter("age"));

		log.info("username = {}, age = {}", username, age);

		res.getWriter().write("ok");
	}

	/**
	 * @RequestParam 사용
	 * - 파라미터 이름으로 바인딩
	 * @ResponseBody 추가
	 * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
	 */
	@ResponseBody // return을 Body에 바로 담아서 반환
	@RequestMapping("/request-param-v2")
	public String requestParamV2(@RequestParam("username") String memberName ,
	                             @RequestParam("age") int memberAge) {

		log.info("username = {}, age = {}", memberName, memberAge);
		return "ok";
	}

	/**
	 * @RequestParam 사용
	 * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
	 * 이거 안돼는데 이유가 뭐지? => intelliJ or gradle 설정을 해줘야 작동 하지만 한눈에 알아보기 어려움으로 추천하지 않는 방법
	 */
	@ResponseBody
	@RequestMapping("/request-param-v3")
	public String requestParamV3(
			@RequestParam String username,
			@RequestParam int age) {
		log.info("username={}, age={}", username, age);
		return "ok";
	}

	/**
	 * @RequestParam 사용
	 * String, int 등의 단순 타입이면 @RequestParam 도 생략 가능
	 * 스프링 부트 3.2 이상부터 작동하지 않음 ( 좀 더 명시적인 표기가 있는 V2 사용 )
	 */
	@ResponseBody
	@RequestMapping("/request-param-v4")
	public String requestParamV4(String username, int age) {
		log.info("username={}, age={}", username, age);
		return "ok";
	}

	/**
	 * @RequestParam.required
	 * /request-param-required -> username이 없으므로 예외
	 *
	 * 주의!
	 * /request-param-required?username= -> 빈문자로 통과
	 *
	 * 주의!
	 * /request-param-required
	 * int age -> null을 int에 입력하는 것은 불가능, 따라서 Integer 변경해야 함(또는 다음에 나오는
	defaultValue 사용)
	 */
	@ResponseBody
	@RequestMapping("/request-param-required")
	public String requestParamRequired(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "age", required = false) Integer age) {
		log.info("username={}, age={}", username, age);
		return "ok";
	}





}
