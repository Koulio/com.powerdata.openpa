package com.powerdata.openpa.pwrflow;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.CloneModelBuilder;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.impl.GenListI;
import com.powerdata.openpa.impl.LoadListI;

/**
 * Remedial adaptive topology control (RATC)
 * 
 * Evaluate power system to recommend switching actions that will alleviate flow
 * on specified branch by calculating a set of Line Outage Distribution Factors (LODF)
 * and applying them to the solved flows
 * 
 * 
 * 
 * The algorithm works as follows:
 * 
 * <ol>
 * <li>Fabricate and place 100MW flow across selected branch</li>
 * <li>Solve flows for fabricated case</li>
 * <li>Calculate fraction of flow in target branch</li>
 * <li>Assign remaining fracction to network, NF</li>
 * <li>LODF for each Line (branch, actually), = flow / 100 / NF</li>
 * </ol>
 * 
 * @author chris@powerdata.com
 *
 */

public class RATCWorker
{
	public static class Result
	{
		
	}
	
	protected PAModel _model;
	protected ACBranch _targ;
	
	public RATCWorker(PAModel model, ACBranch target)
	{
		_model = model;
		_targ = target;
	}
	
	public void runRATC()
	{
//		PAModel clone = cloneModel();
	}
	
	/**
	 * Clone a PAModel with local attributes specific to RATC (powerflow):
	 *
	 * @return
	 */
	PAModel cloneModel()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List<Result> getResults()
	{
		//TODO:  implement this
		return Collections.emptyList();
	}
	
	/**
	 * Create a model builder that can provide a model configured with only a
	 * single generate and load on either side of the target branch
	 * 
	 * @author chris@powerdata.com
	 *
	 */
	class RATCModelBuilder extends CloneModelBuilder
	{
		
		@Override
		protected void loadPrep()
		{
			super.loadPrep();
			
			/* replace entries to return our fabricated data */
			DataLoader<String[]> gname = () -> new String[] {"RATC_SRC"};
			DataLoader<float[]> fzero = () -> new float[] {0f};
			DataLoader<float[]> f200 = () -> new float[] {200f};
			DataLoader<int[]> fbus = () -> new int[] {_targ.getFromBus().getIndex()};
			DataLoader<int[]> tbus = () -> new int[] {_targ.getToBus().getIndex()};
			DataLoader<boolean[]> oos = () -> new boolean[] {false};
			
			_col.put(ColumnMeta.GenID, gname);
			_col.put(ColumnMeta.GenNAME, gname);
			_col.put(ColumnMeta.GenBUS, fbus);
			_col.put(ColumnMeta.GenP, fzero);
			_col.put(ColumnMeta.GenQ, fzero);
			_col.put(ColumnMeta.GenOOS, oos); 
			_col.put(ColumnMeta.GenTYPE, () -> new Gen.Type[] {Gen.Type.Thermal});
			_col.put(ColumnMeta.GenMODE, () -> new Gen.Mode[] {Gen.Mode.MAN});
			_col.put(ColumnMeta.GenOPMINP, fzero);
			_col.put(ColumnMeta.GenOPMAXP, f200);
			_col.put(ColumnMeta.GenMINQ, () -> new float[] {-200f});
			_col.put(ColumnMeta.GenMAXQ, f200);
			_col.put(ColumnMeta.GenPS, () -> new float[] {100f});
			_col.put(ColumnMeta.GenQS, fzero);
			_col.put(ColumnMeta.GenAVR, () -> new boolean[] {true});
			_col.put(ColumnMeta.GenVS, () -> new float[] {1f});
			_col.put(ColumnMeta.GenREGBUS, fbus);
		
			DataLoader<String[]> lname = () -> new String[] {"RATC_LOAD"};
			DataLoader<float[]> pld = () -> new float[]{-100f};
			DataLoader<float[]> qld = () -> new float[]{-10f};
			
			_col.put(ColumnMeta.LoadID, lname);
			_col.put(ColumnMeta.LoadNAME, lname);
			_col.put(ColumnMeta.LoadBUS, tbus);
			_col.put(ColumnMeta.LoadP, pld);
			_col.put(ColumnMeta.LoadQ, qld);
			_col.put(ColumnMeta.LoadOOS, oos);
			_col.put(ColumnMeta.LoadPMAX, pld);
			_col.put(ColumnMeta.LoadQMAX, qld);

		}

		@Override
		protected LoadListI loadLoads() throws PAModelException
		{
			return new LoadListI(_m, 1);
		}



		@Override
		protected GenListI loadGens() throws PAModelException
		{
			return new GenListI(_m, 1);
		}



		public RATCModelBuilder(PAModel srcmdl)
		{
			super(srcmdl, _localCols);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	static Set<ColumnMeta> _localCols = EnumSet.copyOf(Arrays.asList(new ColumnMeta[]
	{
		ColumnMeta.BusVM,
		ColumnMeta.BusVA,
		
		ColumnMeta.ShcapQ,
		ColumnMeta.ShreacQ,
		
		ColumnMeta.SvcQ,
		
		ColumnMeta.LinePFROM,
		ColumnMeta.LinePTO,
		ColumnMeta.LineQFROM,
		ColumnMeta.LineQTO,
		
		ColumnMeta.SercapPFROM,
		ColumnMeta.SercapPTO,
		ColumnMeta.SercapQFROM,
		ColumnMeta.SercapQTO,
			
		ColumnMeta.SerreacPFROM,
		ColumnMeta.SerreacPTO,
		ColumnMeta.SerreacQFROM,
		ColumnMeta.SerreacQTO,
		
		ColumnMeta.PhashPFROM,
		ColumnMeta.PhashPTO,
		ColumnMeta.PhashQFROM,
		ColumnMeta.PhashQTO,
		
		ColumnMeta.TfmrPFROM,
		ColumnMeta.TfmrPTO,
		ColumnMeta.TfmrQFROM,
		ColumnMeta.TfmrQTO,
	}));

	public static void main(String[] args) throws Exception
	{
		String uri = null;
		File poutdir = new File(System.getProperty("user.dir"));
		for(int i=0; i < args.length;)
		{
			String s = args[i++].toLowerCase();
			int ssx = 1;
			if (s.startsWith("--")) ++ssx;
			switch(s.substring(ssx))
			{
				case "uri":
					uri = args[i++];
					break;
				case "outdir":
					poutdir = new File(args[i++]);
					break;
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri "
					+ "[ --outdir output_directory (deft to $CWD ]\n");
			System.exit(1);
		}
		final File outdir = poutdir;
		if (!outdir.exists()) outdir.mkdirs();
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri);
		PAModel base = bldr.load();
		RATCWorker rw = new RATCWorker(base, base.getLines().get(0));
		RATCModelBuilder rmb = rw.new RATCModelBuilder(base);
		new ListDumper().dump(rmb.load(), outdir);
	}

}