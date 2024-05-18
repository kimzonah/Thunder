package com.thunder.interceptor;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
//		String uri = request.getRequestURI();
//		Map<String, String> pathVariables = (Map<String, String>) request
//				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//		
//		String scheduleId = "";
//		if(pathVariables.containsKey("scheduleId")) {
//			scheduleId = scheduleId + pathVariables.get("scheduleId");
//		}
//		
//		String userId = "";
//		if(pathVariables.containsKey("userId")) {
//			userId = userId + pathVariables.get("userId");
//		}
//		
//		
//		// 로그인 없이 접근 가능한 URI 패턴
//		if (uri.matches("/user/signup") || 
//	            uri.matches("/user/login") || 
//	            uri.matches("/user/"+userId) || // "/user/{userId}" 형식의 요청
//	            uri.matches("/thunder") || 
//	            uri.matches("/thunder/"+scheduleId) || // "/thunder/{scheduleId}" 형식의 요청
//	            uri.matches("/thunder/join/"+scheduleId)) { // "/thunder/join/{scheduleId}" 형식의 요청
//	            return true; // 로그인 없이 접근 가능
//	        }
		
		HttpSession session = request.getSession(false);

		// 세션에서 loginUser가 없으면 false
		if(session.getAttribute("loginUser") == null) {
			System.out.println("로그인 유저 없는딩?");
			return false;
		}
		
		System.out.println("로그인 유저 있넹");
		return true;
//		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
