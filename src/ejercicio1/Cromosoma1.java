package ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma1 implements BinaryData<Solucion1> {
	public Cromosoma1(String file) {
		Datos1.iniDatos(file);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Binary; 
	}
	
	@Override
	public Integer size() {
		return Datos1.getNumCandidatos(); 
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double goal = 0.; 
		Double presupuesto = (double) Datos1.getPresupuestoMax();  
		List<Integer> candidatos = new ArrayList<Integer>(); 
		Set<String> cualidades = Datos1.getCualidades(); 
		Boolean flag_presupuesto = false, flag_incompatible = false, flag_cualidades = false; 
		
		for(int i = 0; i < value.size(); i++) { 
			if(value.get(i) == 1){ //Comprueba que se ha seleccionado en el cromosoma y que no se haya violado las restricciones 
				//Funcion objetivo
				goal += Datos1.getValoracion(i); //Funcion objetivo: Maximizar la valoracion
				
				//Restricciones 
				presupuesto -= Datos1.getSueldoMin(i); //Descuento el presupuesto maximo 
				if(presupuesto < 0) flag_presupuesto = true; //Violacion de restriccion de presupuesto 
				
				for(Integer j: candidatos) { //Violacion de restriccion de incompatibilidades
					if(Datos1.getSonIncompatibles(i,j) || Datos1.getSonIncompatibles(j,i)) flag_incompatible = true; 
				}
				
				candidatos.add(i); //Guardo en la lista para poder comprobar imcompatibilidades 
				cualidades.removeAll(Datos1.getCualidades(i)); //Elimina de las cualidades las cualidades del candidato i
			}
		}
		
		if(!cualidades.isEmpty()) flag_cualidades = true; 
		
		return goal - (flag_presupuesto ? 20000 : 0)
					- (flag_incompatible ? 20000: 0)
					- (flag_cualidades ? 20000 : 0); 
	}

	@Override
	public Solucion1 solution(List<Integer> value) {
		return Solucion1.create(value);
	}

}