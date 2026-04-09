package com.clothesstore.controller;

import com.clothesstore.dao.ProductDAO;
import com.clothesstore.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Product> products = productDAO.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
    }
}