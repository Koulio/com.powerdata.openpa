package com.powerdata.openpa.psse;

public abstract class PsseModelIn extends PsseModel implements BaseInputGroup
{
	/** get system base MVA */
	public abstract float getSBASE();
	/** default system base MVA */
	public float getDeftSBASE() {return 100F;}
	
	/** find a Bus by ID */ 
	public BusIn getBus(String id) throws PsseModelException {return getBuses().get(id);}

	@Override
	public IslandList getIslands() throws PsseModelException {return new IslandList(this);}
	@Override
	public GroupList getGroup(String type) throws PsseModelException
	{
		//TODO:
		return null;
		
	}
	@Override
	public String[] getGroupTypes() {return GroupType.values();}
	

	
}
