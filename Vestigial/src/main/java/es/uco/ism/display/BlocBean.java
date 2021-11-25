package es.uco.ism.display;

import java.io.Serializable;

import es.uco.ism.business.bloc.BlocDTO;

public class BlocBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private BlocDTO bloc;
	
	public BlocDTO getBloc() {
		return bloc;
	}
	
	public void setBloc(BlocDTO bloc) {
		this.bloc = bloc;
	}	

}
