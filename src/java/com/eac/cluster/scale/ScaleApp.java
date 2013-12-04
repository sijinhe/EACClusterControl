/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eac.cluster.scale;

import com.eac.cluster.management.NodeManager;

/**
 *
 * @author Sijin
 */
class ScaleApp implements Runnable {

    @Override
    public void run() {
        NodeManager nm = new NodeManager();
        nm.scale();
    }
}
