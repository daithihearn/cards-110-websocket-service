package ie.daithi.websockets.web.security.exception

class AuthenticationException(msg: String, t: Throwable) : org.springframework.security.core.AuthenticationException(msg, t)
