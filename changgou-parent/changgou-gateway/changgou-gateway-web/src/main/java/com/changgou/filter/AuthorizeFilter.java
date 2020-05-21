package com.changgou.filter;


import com.changgou.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Deng
 * @date: 2020-05-16 21:02
 * @description:
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    //令牌头名字
    private static final String AUTHORIZE_TOKEN = "Authorization";


    /***
     * 全局过滤器
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取Request、Response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //获取请求的URI
        String path = request.getURI().getPath();

        //如果是登录、goods等开放的微服务[这里的goods部分开放],则直接放行,这里不做完整演示，完整演示需要设计一套权限系统
//        if (path.startsWith("/api/user/login")||path.startsWith("/api/oauth/login")) {
        if (path.startsWith("/api/user/login")) {
            //放行
            Mono<Void> filter = chain.filter(exchange);
            return filter;
        }
        //获取头文件中的令牌信息
        String tokent = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        //如果头文件中没有，则从请求参数中获取
        if (StringUtils.isEmpty(tokent)) {
            tokent = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }
        if (StringUtils.isEmpty(tokent)) {
            HttpCookie cookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if (cookie != null) {
                tokent = cookie.getValue();
            }
        }
        //如果为空，则输出错误代码
        if (StringUtils.isEmpty(tokent)) {
            response.setStatusCode(HttpStatus.SEE_OTHER);
            //            response.getHeaders().set("Location","http://localhost:8001/api/oauth/login?FROM="+request.getURI().toString());
            //todo 这里可以把也用网关访问,但是要设置很多放行,所以先不做这个
            response.getHeaders().set("Location", "http://localhost:9001/oauth/login?FROM=" + request.getURI().toString());
            return response.setComplete();
        }
        //运行到了这里,说明已经登录了,有令牌,所以,把令牌再传递到下一个微服务
        request.mutate().header(AUTHORIZE_TOKEN, "bearer" + tokent);
//        //解析令牌数据
//        try {
//            Claims claims = JwtUtil.parseJWT(tokent);
//        } catch (Exception e) {
//            e.printStackTrace();
//            //解析失败，响应401错误
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
//        }

        //放行
        return chain.filter(exchange);
    }


    /***
     * 过滤器执行顺序
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}