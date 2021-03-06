/*
 * @(#)UsmUser.java	1.14
 *
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2007 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU General
 * Public License Version 2 only ("GPL") or the Common Development and
 * Distribution License("CDDL")(collectively, the "License"). You may not use
 * this file except in compliance with the License. You can obtain a copy of the
 * License at http://opendmk.dev.java.net/legal_notices/licenses.txt or in the 
 * LEGAL_NOTICES folder that accompanied this code. See the License for the 
 * specific language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file found at
 *     http://opendmk.dev.java.net/legal_notices/licenses.txt
 * or in the LEGAL_NOTICES folder that accompanied this code.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.
 * 
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * 
 *       "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding
 * 
 *       "[Contributor] elects to include this software in this distribution
 *        under the [CDDL or GPL Version 2] license."
 * 
 * If you don't indicate a single choice of license, a recipient has the option
 * to distribute your version of this file under either the CDDL or the GPL
 * Version 2, or to extend the choice of license to its licensees as provided
 * above. However, if you add GPL Version 2 code and therefore, elected the
 * GPL Version 2 license, then the option applies only if the new code is made
 * subject to such option by the copyright holder.
 * 
 *
 */
package com.sun.management.snmp.usm.usmmib;

//
// Generated by mibgen version 5.0 (09/06/01) when compiling SNMP-USER-BASED-SM-MIB.
//

// java imports
//
import java.io.Serializable;

// jmx imports
//
import javax.management.MBeanServer;
import com.sun.management.snmp.SnmpString;
import com.sun.management.snmp.SnmpStatusException;

// jdmk imports
//
import com.sun.management.snmp.agent.SnmpMib;

/**
 * The class is used for implementing the "UsmUser" group.
 * The group is defined with the following oid: 1.3.6.1.6.3.15.1.2.
 *
 *
 * @since Java DMK 5.1
 */
public class UsmUser implements UsmUserMBean, Serializable {
    private static final long serialVersionUID = -3586396890818353357L;

    /**
     * Variable for storing the value of "UsmUserTable".
     * The variable is identified by: "1.3.6.1.6.3.15.1.2.2".
     */
    protected TableUsmUserTable UsmUserTable;

    /**
     * Variable for storing the value of "UsmUserSpinLock".
     * The variable is identified by: "1.3.6.1.6.3.15.1.2.1".
     */
    protected Integer UsmUserSpinLock = new Integer(1);


    /**
     * Constructor for the "UsmUser" group.
     * If the group contains a table, the entries created through an SNMP SET will not be registered in Java DMK.
     */
    public UsmUser(SnmpMib myMib) {
        UsmUserTable = new TableUsmUserTable (myMib);
    }


    /**
     * Constructor for the "UsmUser" group.
     * If the group contains a table, the entries created through an SNMP SET will be AUTOMATICALLY REGISTERED in Java DMK.
     */
    public UsmUser(SnmpMib myMib, MBeanServer server) {
        UsmUserTable = new TableUsmUserTable (myMib, server);
    }

    /**
     * Access the "UsmUserTable" variable.
     */
    public TableUsmUserTable accessUsmUserTable() throws SnmpStatusException {
        return UsmUserTable;
    }

    /**
     * Access the "UsmUserTable" variable as a bean indexed property.
     */
    public UsmUserEntryMBean[] getUsmUserTable() throws SnmpStatusException {
        return UsmUserTable.getEntries();
    }

    /**
     * Getter for the "UsmUserSpinLock" variable.
     */
    public Integer getUsmUserSpinLock() throws SnmpStatusException {
        return UsmUserSpinLock;
    }

    /**
     * Setter for the "UsmUserSpinLock" variable.
     */
    public void setUsmUserSpinLock(Integer x) throws SnmpStatusException {
        UsmUserSpinLock = x;
    }

    /**
     * Checker for the "UsmUserSpinLock" variable.
     */
    public void checkUsmUserSpinLock(Integer x) throws SnmpStatusException {
        //
        // Add your own checking policy.
        //
    }

}
