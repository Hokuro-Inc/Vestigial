package es.uco.ism.display;

import java.io.Serializable;
import java.util.ArrayList;

import es.uco.ism.business.bloc.BlocDTO;

public class BlocBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private BlocDTO bloc;
	private ArrayList<BlocDTO> blocs;
	
	public BlocDTO getBloc() {
		return bloc;
	}
	
	public void setBloc(BlocDTO bloc) {
		this.bloc = bloc;
	}	
	public ArrayList<BlocDTO> getBlocs() {
		return blocs;
	}
	
	public void setBlocs(ArrayList<BlocDTO> blocs) {
		this.blocs = blocs;
	}	
}
