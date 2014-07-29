package com.powerdata.openpa.impl;

import java.util.regex.Pattern;

import com.powerdata.openpa.Area;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusGrpMapBldr;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.GroupListI;
import com.powerdata.openpa.Island;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.Owner;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.Station;
import com.powerdata.openpa.Switch;
import com.powerdata.openpa.VoltageLevel;
import com.powerdata.openpa.Switch.State;

/**
 * Provide a single-bus view (closed switches removed)
 * 
 * @author chris@powerdata.com
 *
 */
public class SingleBusList extends GroupListI<Bus> implements BusList
{
	BusList _buses;
	
	public SingleBusList(PAModelI model) throws PAModelException
	{
		super(model, new BusGrpMapBldr(model)
		{
			@Override
			protected boolean incSW(Switch d) throws PAModelException
			{
				return d.getState() == State.Closed;
			}
		}.addSwitches().getMap(), BusListI._PFld);

		_buses = model.getBuses();
		
	}

	@Override
	public String getName(int ndx) throws PAModelException
	{
		if(_name.rw == null) createNames();
		return super.getName(ndx);
	}
	
	

	@Override
	public String getID(int ndx) throws PAModelException
	{
		if(_id.rw == null) createIDs();
		return super.getID(ndx);
	}

	protected void createNames() throws PAModelException
	{
		String[] nm = new String[_size];
		for(int i=0; i < _size; ++i)
		{
			String s = getStation(i).getName();
			int score = -1;
			Bus sel = null;
			for(Bus b : getBuses(i))
			{
				int ts = scoreBus(b);
				if (ts > score)
				{
					score = ts;
					sel = b;
				}
			}
			StringBuilder ns = new StringBuilder();
			String bn = sel.getName();
			if (!bn.contains(s))
			{
				ns.append(s);
				ns.append(' ');
			}
			ns.append(bn);
			nm[i] = ns.toString();
		}
		_name = new StringData(ColumnMeta.BusNAME)
		{
			@Override
			String[] load() throws PAModelException
			{
				return nm;
			}};
	}
	
	protected void createIDs() throws PAModelException
	{
		String[] id = new String[_size];
		for(int i=0; i < _size; ++i)
			id[i] = String.valueOf(getKey(i));
		_id = new StringData(ColumnMeta.BusID)
		{
			@Override
			String[] load() throws PAModelException
			{
				return id;
			}
		};
	}

	static final Pattern[] _Scores = new Pattern[] 
	{
		null,
		Pattern.compile("^[a-zA-Z ]+$"),
		Pattern.compile("^[^0-9]+.*$"),
	};
	
	protected int scoreBus(Bus b) throws PAModelException
	{
		int score = 0;
		String bn = b.getName();
		
		for(int i=1; i < _Scores.length; ++i)
		{
			if(_Scores[i].matcher(bn).matches())
				score = i;
		}
		return score;
	}

	@Override
	public String[] getName() throws PAModelException
	{
		if(_name.rw == null) createNames();
		return super.getName();
	}

	@Override
	public float getVM(int ndx) throws PAModelException
	{
		return getBuses(ndx).getVM(0);
	}

	@Override
	public void setVM(int ndx, float vm) throws PAModelException
	{
		for (Bus b : getBuses(ndx))
			b.setVM(vm);
	}

	@Override
	public float[] getVM() throws PAModelException
	{
		float[] rv = new float[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = getVM(i);
		return rv;
	}

	@Override
	public void setVM(float[] vm) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			setVM(i, vm[i]);
	}

	@Override
	public float getVA(int ndx) throws PAModelException
	{
		return getBuses(ndx).getVA(0);
	}

	@Override
	public void setVA(int ndx, float va) throws PAModelException
	{
		for(Bus b : getBuses(ndx))
			b.setVA(va);
	}

	@Override
	public float[] getVA() throws PAModelException
	{
		float[] rv = new float[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = getVA(i);
		return rv;
	}

	@Override
	public void setVA(float[] va) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			setVA(i, va[i]);
	}

	@Override
	public int getFreqSrcPri(int ndx) throws PAModelException
	{
		int rv = Integer.MIN_VALUE;
		for(Bus b : getBuses(ndx))
		{
			int p = b.getFreqSrcPri();
			if (rv < p) rv = p;
		}
		return rv;
	}

	@Override
	public void setFreqSrcPri(int ndx, int fsp) throws PAModelException
	{
		for(Bus b: getBuses(ndx))
			b.setFreqSrcPri(fsp);
	}

	@Override
	public int[] getFreqSrcPri() throws PAModelException
	{
		int[] rv = new int[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = getFreqSrcPri(i);
		return rv;
	}

	@Override
	public void setFreqSrcPri(int[] fsp) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			setFreqSrcPri(i, fsp[i]);
	}

	@Override
	public Island getIsland(int ndx) throws PAModelException
	{
		return getBuses(ndx).getIsland(0);
	}

	@Override
	public Area getArea(int ndx) throws PAModelException
	{
		return getBuses(ndx).getArea(0);
	}

	@Override
	public void setArea(int ndx, Area a) throws PAModelException
	{
		for(Bus b : getBuses(ndx))
			b.setArea(a);
	}

	@Override
	public Area[] getArea() throws PAModelException
	{
		Area[] rv = new Area[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = getArea(i);
		return rv;
	}

	@Override
	public void setArea(Area[] a) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			setArea(i, a[i]);
	}

	@Override
	public Station getStation(int ndx) throws PAModelException
	{
		return getBuses(ndx).getStation(0);
	}

	@Override
	public void setStation(int ndx, Station s) throws PAModelException
	{
		for(Bus b : getBuses(ndx))
			b.setStation(s);
	}

	@Override
	public Station[] getStation() throws PAModelException
	{
		Station[] rv = new Station[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = getStation(i);
		return rv;
	}

	@Override
	public void setStation(Station[] s) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			setStation(i, s[i]);
	}

	@Override
	public Owner getOwner(int ndx) throws PAModelException
	{
		return getBuses(ndx).getOwner(0);
	}

	@Override
	public void setOwner(int ndx, Owner o) throws PAModelException
	{
		for(Bus b : getBuses(ndx))
			b.setOwner(o);
	}

	@Override
	public Owner[] getOwner() throws PAModelException
	{
		Owner[] rv = new Owner[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = getOwner(i);
		return rv;
	}

	@Override
	public void setOwner(Owner[] o) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			setOwner(i, o[i]);
	}

	@Override
	public VoltageLevel getVoltageLevel(int ndx) throws PAModelException
	{
		return getBuses(ndx).getVoltageLevel(0);
	}

	@Override
	public void setVoltageLevel(int ndx, VoltageLevel l) throws PAModelException
	{
		for(Bus b : getBuses(ndx))
			b.setVoltageLevel(l);
	}

	@Override
	public VoltageLevel[] getVoltageLevel() throws PAModelException
	{
		VoltageLevel[] rv = new VoltageLevel[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = getVoltageLevel(i);
		return rv;
	}

	@Override
	public void setVoltageLevel(VoltageLevel[] l) throws PAModelException
	{
		for (int i=0; i < _size; ++i)
			setVoltageLevel(i, l[i]);
	}

	@Override
	public Bus get(int index)
	{
		return new Bus(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.AnonymousGroup;
	}
	
}