package com.powerdata.openpa.psse;

public class IslandIn extends GroupIn
{
	protected IslandInList _list;
	
	public IslandIn(int ndx, IslandInList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public BusInList getBuses() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenInList getGenerators() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoadInList getLoads() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LineInList getLines() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransformerInList getTransformers() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PhaseShifterInList getPhaseShifters() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SwitchedShuntInList getSwitchedShunts() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupInList getGroup(String type) throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getGroupTypes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IslandInList getIslands() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SwitchPsseList getSwitches() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getObjectID() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
