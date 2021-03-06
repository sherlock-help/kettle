/* Copyright (c) 2010 Aschauer EDV GmbH.  All rights reserved. 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * This software was developed by Aschauer EDV GmbH and is provided under the terms 
 * of the GNU Lesser General Public License, Version 2.1. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.gnu.org/licenses/lgpl-2.1.txt.
 *
 * Software distributed under the GNU Lesser Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 * 
 * Please contact Aschauer EDV GmbH www.aschauer-edv.at if you need additional
 * information or have any questions.
 * 
 * @author  Robert Wintner robert.wintner@aschauer-edv.at
 * @since   PDI 4.0
 */

package org.pentaho.di.trans.steps.sapinput.sap;

public class SAPLibraryTester {
	
	private static final String JCO_LIB_EXISTENCE_TEST_CLASS = "com.sap.conn.jco.JCoDestinationManager";

	private static final String JCO_IMPL_EXISTENCE_TEST_CLASS = "com.sap.conn.rfc.driver.CpicDriver";

	public static boolean isJCoLibAvailable() {
		try {
			Object c = Class.forName(JCO_LIB_EXISTENCE_TEST_CLASS);
			if (c == null)
				return false;
			return true;
		} catch (NoClassDefFoundError e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean isJCoImplAvailable() {
		// we simply skip the test because it does not work for sapjco3 >=3.0.2
		return true;
	}

	
	public static boolean isJCoImplAvailableNotUsed() {
		try {
			Object c = Class.forName(JCO_IMPL_EXISTENCE_TEST_CLASS);
			if (c == null)
				return false;
			return true;
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
			return false;
		} catch (NoClassDefFoundError e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
	}

}
