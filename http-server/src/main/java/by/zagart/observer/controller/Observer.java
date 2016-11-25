package by.zagart.observer.controller;

import by.zagart.observer.model.services.impl.ModuleServiceImpl;
import by.zagart.observer.model.services.impl.StandServiceImpl;
import by.zagart.observer.utils.DataUtil;
import by.zagart.observer.utils.HttpUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RequestHandler")
public class Observer extends HttpServlet {
    private Handler mHandler;

    protected void doGet(HttpServletRequest pRequest, HttpServletResponse pResponse)
            throws ServletException, IOException {
        Logger.log(HttpUtil.getRequestInfo(pRequest));
        mHandler.handleGetRequest(pRequest, pResponse);
    }

    protected void doPost(HttpServletRequest pRequest, HttpServletResponse pResponse)
            throws ServletException, IOException {
        Logger.log(HttpUtil.getRequestInfo(pRequest));
        mHandler.handlePostRequest(pRequest, pResponse);
    }

    @Override
    public void init() throws ServletException {
        mHandler = new Handler();
        for (int i = 0; i < 10000; i++) {
            final StandServiceImpl standService = new StandServiceImpl();
            final ModuleServiceImpl moduleService = new ModuleServiceImpl();
            DataUtil.persistPairStandModule(
                    DataUtil.getNewStand(),
                    DataUtil.getNewModule(),
                    standService,
                    moduleService
            );
        }
    }
}

