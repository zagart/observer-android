package by.zagart.observer.controller.servlets;

import by.zagart.observer.controller.Handler;
import by.zagart.observer.controller.Logger;
import by.zagart.observer.model.entities.Module;
import by.zagart.observer.model.entities.Stand;
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
    private static final int DATABASE_INITIAL_FILL_VALUE = 100;
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
        final StandServiceImpl standService = new StandServiceImpl();
        final ModuleServiceImpl moduleService = new ModuleServiceImpl();
        for (int i = 0; i < DATABASE_INITIAL_FILL_VALUE; i++) {
            final Stand newStand = DataUtil.getNewStand();
            final Module newModule = DataUtil.getNewModule();
            DataUtil.persistPairStandModule(
                    newStand,
                    newModule,
                    standService,
                    moduleService
            );
            DataUtil.persistPairStandModule(
                    newStand,
                    newModule,
                    standService,
                    moduleService
            );
        }
    }
}

