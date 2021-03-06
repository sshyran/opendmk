/*
 * @(#)file      ASTMib.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.16
 * @(#)lastedit  07/03/08
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


/* Generated By:JJTree: Do not edit this line. ASTMib.java */

package com.sun.jdmk.tools.mibgen;

import java.lang.*;
import java.util.*;

public class ASTMib extends SimpleNode {
  
    protected ASTModuleIdentifier moduleIdentifier;
    protected Hashtable registrationTable = new Hashtable();
    protected Hashtable objectIdentities  = new Hashtable();
    protected Hashtable objectGroups      = new Hashtable();
    protected Hashtable notifGroups       = new Hashtable();
    protected Hashtable notificationTypes = new Hashtable();
    protected Hashtable valueDefs= new Hashtable();
    protected Hashtable syntaxDefs= new Hashtable();
    protected Hashtable oidTranslation= new Hashtable();
    protected Hashtable symbolTranslation= new Hashtable();
    protected Hashtable v1= new Hashtable();
    protected Hashtable v2= new Hashtable();
    protected MibTree tree= null;
  
    private ModulesHandler modulesHandler;

    private boolean useDescription= true;
  
  
    /**
     * In order to improve performance, we are going to use three different
     * handlers for the symbols: one for the identifiers, one for the syntaxes, one for
     * the symbols defined in the index clause. (The latest will allow to handle cross-referenced
     * index prior to generating code).
     */
    protected IdentifierHandler identifierHandler;
    protected IdentifierHandler indexHandler;
    protected SyntaxHandler syntaxHandler;
  
    ASTMib(int id) {
        super(id);
    }

    ASTMib(Parser p, int id) {
        super(p, id);
    }

    public static Node jjtCreate(int id) {
        return new ASTMib(id);
    }

    public static Node jjtCreate(Parser p, int id) {
        return new ASTMib(p, id);
    }

    public void registerValueDef(String symbolName, SimpleNode aNode) {
        if (valueDefs.containsKey(symbolName)) {
            Trace.warning(MessageHandler.getMessage("compile.w.multiple.value", 
                                                    symbolName, 
                                                    getModuleName()));
        }
        valueDefs.put(symbolName, aNode);
    }
  
    public void registerSymbol(String symbolName, SimpleNode aNode)
	throws SemanticException {
	if (registrationTable.containsKey(symbolName)) {
            throw new 
		SemanticException(MessageHandler.getMessage("compile.error.multiple.register",
							    symbolName, 
							    getModuleName()));
	}
	registrationTable.put(symbolName,aNode);	
    }

    public void registerObjectIdentity(String symbolName, SimpleNode aNode)
	throws ParseException {
        if (objectIdentities.containsKey(symbolName)) {
            Trace.error(MessageHandler.getMessage("compile.error.multiple.objectidentity", 
						  symbolName, 
						  getModuleName()));
        }
        objectIdentities.put(symbolName, aNode);
	registerSymbol(symbolName,aNode);
    }

    public void registerObjectGroup(String symbolName, SimpleNode aNode)
	throws ParseException {
        if (objectGroups.containsKey(symbolName)) {
            Trace.error(MessageHandler.getMessage("compile.error.multiple.objectgroup", 
						  symbolName, 
						  getModuleName()));
        }
        objectGroups.put(symbolName, aNode);
	registerSymbol(symbolName,aNode);
    }

    public void registerNotifGroup(String symbolName, SimpleNode aNode)
	throws ParseException {
        if (notifGroups.containsKey(symbolName)) {
            Trace.error(MessageHandler.getMessage("compile.error.multiple.notifgroup", 
						  symbolName, 
						  getModuleName()));
        }
        notifGroups.put(symbolName, aNode);
	registerSymbol(symbolName,aNode);
    }

    public void registerNotificationType(String symbolName, SimpleNode aNode)
	throws ParseException {
        if (notificationTypes.containsKey(symbolName)) {
            Trace.error(MessageHandler.getMessage("compile.error.multiple.notificationtype", 
						  symbolName, 
						  getModuleName()));
        }
        notificationTypes.put(symbolName, aNode);
	registerSymbol(symbolName,aNode);
    }
  
    public void registerSyntaxDef(String symbolName, SimpleNode aNode) {
        if (syntaxDefs.containsKey(symbolName)) {
            Trace.warning(MessageHandler.getMessage("compile.w.multiple.syntax", 
                                                    symbolName, 
                                                    getModuleName()));
        }
        syntaxDefs.put(symbolName, aNode);
    }
  
    public void registerV1ObjectType(String symbolName, ASTObjectTypeDefinition aNode) throws ParseException {
        if (v1.containsKey(symbolName)) {
            throw new SemanticException(MessageHandler.getMessage("compile.w.multiple.v1Object", 
                                                                  symbolName, 
                                                                  getModuleName()));
        }
     
        v1.put(symbolName, aNode);
	registerSymbol(symbolName,aNode);
    }
    
    public void registerV2ObjectType(String symbolName, ASTObjectTypeDefinition aNode) throws ParseException {
        if (v2.containsKey(symbolName)) {
            throw new SemanticException(MessageHandler.getMessage("compile.w.multiple.v2Object", 
                                                                  symbolName, 
                                                                  getModuleName()));
        }
        v2.put(symbolName, aNode);
	registerSymbol(symbolName,aNode);
    }
  
    public void localResolve() {
   
        identifierHandler= new IdentifierHandler(getModuleName());
        indexHandler= new IdentifierHandler(getModuleName());
        syntaxHandler= new SyntaxHandler(getModuleName());
    
        Trace.info(MessageHandler.getMessage("compile.resolve.local", getModuleName()));
        resolveValues();
	resolveObjectIdentities();
	resolveObjectGroups();
	resolveNotifGroups();
	resolveNotificationTypes();
        resolveSyntaxes();
        resolveAllObjectTypes();   
    }
 
    public void globalResolve(Hashtable idTable, Hashtable refTable, Hashtable indexTable) {
        Trace.info(MessageHandler.getMessage("compile.resolve.global", getModuleName()));
        identifierHandler.resolve(idTable);
        syntaxHandler.resolve(refTable);
        indexHandler.resolve(indexTable);
    }
  
    /**
     * Translate the specified symbol name into the dot notation string,
     * within the scope of this MIB. 
     */
    public String resolveOidSymbol(String symbolName) {
        String dotString = null;

	dotString = getAssociatedOid(symbolName);

	// If a symol was found, return it
	if (dotString != null && dotString.equals(""))
	    dotString = null;

        return dotString;
    }
 
    /**
     * Translate the specified symbol name into the dot notation string.
     * It first look among all the variables of this MIB.
     * If there is no result available, it looks at the other MIBs 
     * registered into the ModuleHandler.
     */
    public String translateOid(String symboleName) {
        
        String dotString = null;
    
	dotString = resolveOidSymbol(symboleName);
        
        if (dotString != null)
            return dotString;
        
        // Search among the other MIBs registered into the ModuleHandler.
        Enumeration e = getModuleHandler().mibElements();
        for (; e.hasMoreElements(); ) {
            ASTMib aMib = (ASTMib)e.nextElement();
            
	    dotString = aMib.resolveOidSymbol(symboleName);
	    if (dotString != null) return dotString;

	}
        
        return null;
    }
    
    /**
     * Get the dot notation string corresponding to the specified symbol name.
     */
    public String getValueDef(String symboleName) throws SemanticException {
        
        SimpleNode val = (SimpleNode)valueDefs.get(symboleName);
        if (val == null)
            return null;
	Vector ref = new Vector();
	ref.addElement(val);
        return (val.computeValue(ref)).toString();
    }
    
    /**
     * Get module name
     */
    public String getModuleName() {
        return moduleIdentifier.getModuleName();
    }
  
    /**
     * Link to a module handler
     */
    public ModulesHandler getModuleHandler() {
        return modulesHandler;
    }
  
    /**
     * Link to a module handler
     */
    public void setModulesHandler(ModulesHandler x) {
        modulesHandler= x;
    }
  
    /**
     * Build a tree structure for representing the MIB.
     */
    public boolean buildMibTree() {
	buildOidTable();
        tree= new MibTree(getModuleName());
        boolean result= tree.buildTree(v1, v2);    
        return result;
    }
  
    public MibTree getMibTree() {
        return tree;
    }
  
    public void buildOidTable() {
        // Now build a table containing all the values
        //
        for(Enumeration e= valueDefs.keys(); e.hasMoreElements(); ) {
            String key= (String) e.nextElement();
            SimpleNode valueDef= (SimpleNode) valueDefs.get(key);
            Vector ref= new Vector();
            ref.addElement(valueDef);
            try {
                StringBuffer val= valueDef.computeValue(ref);
                if (val.length() != 0) {
                    oidTranslation.put(val.toString(), key);
		    symbolTranslation.put(key,val.toString());
                }
            } catch(SemanticException a) {
            }   
        }

	// Add the registered symbols to the table
	//
        for(Enumeration e= registrationTable.keys(); e.hasMoreElements(); ) {
            String key= (String) e.nextElement();
            RegisteredObject obj = (RegisteredObject) 
		registrationTable.get(key);
	    ASTOidValue oid = obj.getOidNode();
            Vector ref = new Vector();
            ref.addElement(oid);
            try {
                StringBuffer val= oid.computeValue(ref);
                if (val.length() != 0) {
                    oidTranslation.put(val.toString(), key);
		    symbolTranslation.put(key,val.toString());
                }
            } catch(SemanticException a) {
            }   
        }
    }
  
    /**
     * Print tree structure
     */
    public void printMibTree(String prefix) {
        tree.dump(prefix);
    }
    
    public Hashtable getOidTranslation() {
        return oidTranslation;
    }

    public Hashtable getSymbolTranslation() {
        return symbolTranslation;
    }

    public String getAssociatedOid(String symbol) {
	return (String) symbolTranslation.get(symbol);
    }

    public String getAssociatedSymbol(String dotOid) {
	return (String) oidTranslation.get(dotOid);
    }

    /**
     * Once the symbol resolution is done, we can compute whatever value.
     * Be careful: some loops can be detected ...
     */
    public boolean computeValues() {
        if (computeAllObjectTypeOid() == false) 
	    return false;
	if (computeObjectIdentityOid() == false)
	    return false;
	if (computeObjectGroupOid() == false)
	    return false;
	if (computeNotifGroupOid() == false)
	    return false;
	if (computeNotificationTypeOid() == false)
	    return false;
	return true;
    }
  
    public IdentifierHandler getIdResolver() {
        return identifierHandler;
    }
  
    public IdentifierHandler getIndexResolver() {
        return indexHandler;
    }
  
    public SyntaxHandler getRefResolver() {
        return syntaxHandler;
    }
  
    public boolean printUndefinedSymbols() {
        return (identifierHandler.printUnresolved() && 
                syntaxHandler.printUnresolved() &&
                indexHandler.printUnresolved());
    }
        
    // PRIVATE METHODS
    //----------------
  
    private void resolveValues() {
        // Go through the list of values. Only resolve integer and oid ...
        //
        for(Enumeration e= valueDefs.keys(); e.hasMoreElements(); ) {
            String key= (String) e.nextElement();
            SimpleNode valueDef= (SimpleNode) valueDefs.get(key);
            //ASTValue aValue= (ASTValue) valueDef.getChildOfType(ParserTreeConstants.JJTVALUE);
            // We are only going to process integer and oid ...
            //
            //if (aValue== null)
            //	continue;
            //if (aValue.children == null)
            //	continue;
            // SimpleNode child= (SimpleNode) aValue.children[0];
            valueDef.resolve(identifierHandler, key);
      
        }
    
    }
  
    private void resolveSyntaxes() {
        // Go through the list of defined syntaxes.
        //
        for(Enumeration e= syntaxDefs.elements(); e.hasMoreElements(); ) {
            SimpleNode aSyntax= (SimpleNode)e.nextElement();
            aSyntax.resolve(syntaxHandler);
        }
    
    }
  
    private void resolveAllObjectTypes() {
        resolveObjectType(v1);
        resolveObjectType(v2);
    }
  
    private void resolveObjectIdentities() {
        // Go through the list of values. Only resolve integer and oid ...
        //
        for(Enumeration e= objectIdentities.keys(); e.hasMoreElements(); ) {
            String key= (String) e.nextElement();
            ASTObjectIdentity objectIdentity= (ASTObjectIdentity) 
		objectIdentities.get(key);
            ASTOidValue oid= objectIdentity.getOidNode();
            oid.resolve(identifierHandler, key);
        }
    
    }
  
    private void resolveObjectGroups() {
        // Go through the list of values. Only resolve integer and oid ...
        //
        for(Enumeration e= objectGroups.keys(); e.hasMoreElements(); ) {
            String key= (String) e.nextElement();
            ASTObjectGroup objectGroup= (ASTObjectGroup) 
		objectGroups.get(key);
            ASTOidValue oid= objectGroup.getOidNode();
            oid.resolve(identifierHandler, key);
        }
    
    }
  
    private void resolveNotifGroups() {
        // Go through the list of values. Only resolve integer and oid ...
        //
        for(Enumeration e= notifGroups.keys(); e.hasMoreElements(); ) {
            String key= (String) e.nextElement();
            ASTNotificationGroup notifGroup= (ASTNotificationGroup) 
		notifGroups.get(key);
            ASTOidValue oid= notifGroup.getOidNode();
            oid.resolve(identifierHandler, key);
        }
    
    }
  
    private void resolveNotificationTypes() {
        // Go through the list of values. Only resolve integer and oid ...
        //
        for(Enumeration e= notificationTypes.keys(); e.hasMoreElements(); ) {
            String key= (String) e.nextElement();
            ASTNotificationType notificationType = (ASTNotificationType) 
		notificationTypes.get(key);
            ASTOidValue oid= notificationType.getOidNode();
            oid.resolve(identifierHandler, key);
        }
    
    }
  
    private void resolveObjectType(Hashtable table) {
        for(Enumeration e= table.keys(); e.hasMoreElements();) {
            String key= (String) e.nextElement();
            ASTObjectTypeDefinition obj= (ASTObjectTypeDefinition) 
		table.get(key);
      
            // Well, easy to say but the symbol is resolved ...
            // Might need it if used as an index
            //
            indexHandler.addResolution(key, obj);
      
            // Get the object definition
            //
            ObjectTypeDefinition definition= obj.getDefinition();
      
            // Access the oid
            //
            ASTOidValue oid= definition.getOidNode();
            oid.resolve(identifierHandler, key);
      
            // Access the syntax
            //
            ASTNamedType syntax= obj.getSyntax();
      
            // Resolve the defined syntax
            //
            syntax.resolve(syntaxHandler);
      
            // DEFVAL for OBJECT IDENTIFIER values:
            // Resolve for ASTOidValue default value.
            //
            ASTValue defVal = definition.getDefValue();
            if (defVal != null) {
                // We need to keep the syntax of the OBJECT TYPE owning this default value.
                defVal.setDefValSnmpSyntax(SyntaxMapper.getIntSnmpSyntax(obj.getSnmpSyntax()));
                // We do not need to add the symbol in the SymbolHandler table
                // (the symbol should already be registered in the table)
                defVal.resolve(identifierHandler);
            }
      
            // Access the index clause
            //
            Node node= definition.getIndex();
            if (node != null) {
                // Too bad we are dealing with a table
                //
                resolveIndexOfTable(node);
            }
      
        }
    }
  
    private void resolveIndexOfTable(Node aNode) {
        SimpleNode index= (SimpleNode) aNode;
        index.validateIndexNames(indexHandler);
    }
  
    private boolean computeNotificationTypeOid() {
        boolean ok= true;
        for(Enumeration e= notificationTypes.keys(); e.hasMoreElements();) {
            String key= (String) e.nextElement();
	    ASTNotificationType obj = (ASTNotificationType) 
		notificationTypes.get(key);

            ASTOidValue oid = obj.getOidNode();
            Vector ref= new Vector();
            ref.addElement(oid);
            try {
                StringBuffer res = oid.computeValue(ref); 
            } catch(SemanticException a) {
                String val= a.getMessage();
                Trace.error(MessageHandler.getMessage("compile.error.loop", key, val, getModuleName()));
                ok= false;
            }
        }
        return ok;
    }

    
    private boolean computeObjectIdentityOid() {
        boolean ok= true;
        for(Enumeration e= objectIdentities.keys(); e.hasMoreElements();) {
            String key= (String) e.nextElement();
	    ASTObjectIdentity obj = (ASTObjectIdentity) 
		objectIdentities.get(key);

            ASTOidValue oid = obj.getOidNode();
            Vector ref= new Vector();
            ref.addElement(oid);
            try {
                StringBuffer res = oid.computeValue(ref); 
            } catch(SemanticException a) {
                String val= a.getMessage();
                Trace.error(MessageHandler.getMessage("compile.error.loop", key, val, getModuleName()));
                ok= false;
            }
        }
        return ok;
    }

    
    private boolean computeObjectGroupOid() {
        boolean ok= true;
        for(Enumeration e= objectGroups.keys(); e.hasMoreElements();) {
            String key= (String) e.nextElement();
	    ASTObjectGroup obj = (ASTObjectGroup) 
		objectGroups.get(key);

            ASTOidValue oid = obj.getOidNode();
            Vector ref= new Vector();
            ref.addElement(oid);
            try {
                StringBuffer res = oid.computeValue(ref); 
            } catch(SemanticException a) {
                String val= a.getMessage();
                Trace.error(MessageHandler.getMessage("compile.error.loop", key, val, getModuleName()));
                ok= false;
            }
        }
        return ok;
    }

    
    private boolean computeNotifGroupOid() {
        boolean ok= true;
        for(Enumeration e= notifGroups.keys(); e.hasMoreElements();) {
            String key= (String) e.nextElement();
	    ASTNotificationGroup obj = (ASTNotificationGroup) 
		notifGroups.get(key);

            ASTOidValue oid = obj.getOidNode();
            Vector ref= new Vector();
            ref.addElement(oid);
            try {
                StringBuffer res = oid.computeValue(ref); 
            } catch(SemanticException a) {
                String val= a.getMessage();
                Trace.error(MessageHandler.getMessage("compile.error.loop", key, val, getModuleName()));
                ok= false;
            }
        }
        return ok;
    }

    
    private boolean computeAllObjectTypeOid() {
        return computeObjectTypeOid(v1) && computeObjectTypeOid(v2);
    }

    private boolean computeObjectTypeOid(Hashtable table)  {
        boolean ok= true;
        for(Enumeration e= table.keys(); e.hasMoreElements();) {
            String key= (String) e.nextElement();
            ASTObjectTypeDefinition obj= (ASTObjectTypeDefinition) table.get(key);
      
            // Get the object definition
            //
            ObjectTypeDefinition definition= obj.getDefinition();
      
            // Access the oid
            //
            ASTOidValue oid= definition.getOidNode();
            Vector ref= new Vector();
            ref.addElement(oid);
            try {
                StringBuffer res= oid.computeValue(ref); 
                //	Trace.info("KEY= " + key + " OID= " + res.toString());
            } catch(SemanticException a) {
                String val= a.getMessage();
                Trace.error(MessageHandler.getMessage("compile.error.loop", key, val, getModuleName()));
                ok= false;
            }
        }
        return ok;
    }

    public void setDescriptionOn() {
        useDescription= true;
    }

    public void setDescriptionOff() {
        useDescription= false;
    }
 
    public boolean isDescriptionOn() {
        return useDescription;
    }

    public void setDescriptionHandling(boolean val) {
        useDescription= val;
    }
}
