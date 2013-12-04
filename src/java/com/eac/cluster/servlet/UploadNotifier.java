/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eac.cluster.servlet;

import com.eac.db.entity.Container;
import com.eac.entity.operation.APPAction;
import com.eac.entity.operation.ClusterAction;
import com.eac.tool.json.JSONContainer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sijin
 */
public class UploadNotifier extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String containerid = request.getParameter("containerid");

            APPAction aact = new APPAction();

            String json = "";

            JSONContainer jc = new JSONContainer();

            if (aact.uploadAPP(containerid)) {

                if (aact.modifyWAR(containerid)) {

                    if (aact.notifyDownload(containerid)) {

                        Container container = aact.getAPP(containerid);

                        ClusterAction ca = new ClusterAction();

                        if (ca.changeClusterSetting()) {
                            container = aact.startAPP(container);

                            if (container != null) {

                                json = jc.createSimpleJSON(true, "Upload APP [Start APP Success]");
                                json = request.getParameter("callback") + "(" + json + ")";
                            } else {

                                json = jc.createSimpleJSON(false, "Upload APP [Start APP failed]");
                                json = request.getParameter("callback") + "(" + json + ")";
                            }

                        } else {
                            json = jc.createSimpleJSON(false, "Upload APP [Apache Reload failed]");
                            json = request.getParameter("callback") + "(" + json + ")";
                        }

                    } else {
                        json = jc.createSimpleJSON(false, "Upload APP [Download APP failed]");
                        json = request.getParameter("callback") + "(" + json + ")";

                    }


                } else {

                    json = jc.createSimpleJSON(false, "Upload APP [Modify WAR Failure]");
                    json = request.getParameter("callback") + "(" + json + ")";

                }





            } else {
                json = jc.createSimpleJSON(false, "Upload APP [Upload failed]");
                json = request.getParameter("callback") + "(" + json + ")";
            }

            out.println(json);

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
