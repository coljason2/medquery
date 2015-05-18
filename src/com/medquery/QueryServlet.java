package com.medquery;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.medquery.comman.MedEntity;
import com.medquery.comman.getQuery;

public class QueryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final Logger log = Logger.getLogger(QueryServlet.class.getName());

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		getQuery getmed = new getQuery();
		log.info("querystring:" + req.getParameter("querystring"));
		List<MedEntity> meds = getmed.getMedicine(req
				.getParameter("querystring"));
		log.info(meds.toString());
		req.setAttribute("meds", meds);
		RequestDispatcher view = req.getRequestDispatcher("resault.jsp");
		view.forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
