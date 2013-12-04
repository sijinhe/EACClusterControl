/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eac.cluster.monitor;

import com.eac.cluster.management.NodeManager;

/**
 *
 * @author Sijin
 */
public class UpdateData implements Runnable {

    @Override
    public void run() {
        NodeManager nm = new NodeManager();
        nm.monitor();
    }
}
