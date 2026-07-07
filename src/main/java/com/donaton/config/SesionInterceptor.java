package com.donaton.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

/**
 * Verifica, en cada petición a una ruta protegida, que exista una sesión
 * con un usuario logueado (usuarioId en la sesión).
 *
 * También desactiva el caché del navegador para estas páginas: así, si el
 * usuario cierra sesión y luego presiona "atrás", el navegador no muestra
 * la página guardada en su historial, sino que vuelve a pedirla al servidor
 * (y este interceptor lo redirige al login).
 */
public class SesionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // No cachear páginas protegidas (evita el problema del botón "atrás" tras cerrar sesión)
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession(false); // false: no crear una sesión nueva solo para revisarla
        boolean logueado = (session != null && session.getAttribute("usuarioId") != null);

        if (!logueado) {
            String destino = request.getRequestURI();
            if (request.getQueryString() != null) {
                destino += "?" + request.getQueryString();
            }
            String destinoCodificado = UriUtils.encode(destino, StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/login?next=" + destinoCodificado);
            return false;
        }

        return true;
    }
}
