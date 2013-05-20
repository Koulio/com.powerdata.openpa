package com.powerdata.openpa.psse;

import com.powerdata.openpa.psse.BusList.BusTypeCode;
import com.powerdata.openpa.tools.BaseObject;

public class Bus extends BaseObject
{
	protected BusList<?> _list;
	
	public Bus(int ndx, BusList<?> list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}
	
	/* Convenience methods */
	
	/** enumerated IDE code */
	public BusTypeCode getBusType() {return _list.getBusType(_ndx);}
	/** Area */
	public AreaInterchange getAreaObject() {return _list.getAreaObject(_ndx);}
	/** Zone */
	public Zone getZoneObject() {return _list.getZoneObject(_ndx);}
	/** Owner */
	public Owner getOwnerObject() {return _list.getOwnerObject(_ndx);}
	
	/** Active component of shunt admittance to ground (GL) per-unit on 100MVA base */
	public float getShuntConductance() {return _list.getShuntConductance(_ndx);}
	/** Reactive component of shunt admittance to ground (BL) per-unit on 100MVA base */
	public float getShuntSusceptance() {return _list.getShuntSusceptance(_ndx);}
	/** Bus voltage phase angle in radians */
	public float getVangRad() {return _list.getVangRad(_ndx);}
	
	
	
	/* Raw PSS/e methods */
	
	/** Bus number */
	public int getI() {return _list.getI(_ndx);}
	/** Alphanumeric identifier */
	public String getNAME() {return _list.getNAME(_ndx);}
	/** Bus base voltage */
	public float getBASKV() {return _list.getBASKV(_ndx);}
	/** Bus type code */
	public int getIDE() {return _list.getIDE(_ndx);}
	/** Active component of shunt admittance to ground */
	public int getGL() {return _list.getGL(_ndx);}
	/** Reactive component of shunt admittance to ground */
	public int getBL() {return _list.getBL(_ndx);}
	/** Area number */
	public int getAREA() {return _list.getAREA(_ndx);}
	/** Zone number */
	public int getZONE() {return _list.getZONE(_ndx);}
	/** Bus voltage magnitude p.u.*/
	public float getVM() {return _list.getVM(_ndx);}
	/** Bus voltage phase angle in degrees */
	public float getVA() {return _list.getVA(_ndx);}
	/** Owner number */
	public int getOWNER() {return _list.getOWNER(_ndx);}
	
}