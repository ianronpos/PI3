package ejercicio2;

import java.util.List;

import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma2 implements RangeIntegerData<Solucion2>{
	public Cromosoma2(String file) {
		Datos2.iniDatos(file);
	}
	
	@Override
	public ChromosomeType type() {
		return ChromosomeType.RangeInteger; 
	} 

	@Override
	public Integer max(Integer i) {
		return Datos2.getUnidsSemanaProd(i) + 1; 
	}

	@Override
	public Integer min(Integer i) {
		return 0; 
	}

	@Override
	public Integer size() {
		return Datos2.getNumProductos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double goal = 0.; 
		Integer t_prod = Datos2.getTiempoProdTotal(); 
		Integer t_elab = Datos2.getTiempoElabTotal();
		Integer cantidad = 0; 
		
		for(int i = 0; i < value.size(); i++) { 
			cantidad = value.get(i); 
			if(cantidad > 0) {	
				//Funcion objetivo, maximizar el beneficio
				goal += Datos2.getPrecioProd(i) * cantidad; 
			
				//No se supere el tiempo de produccion maximo semanal
				t_prod = t_prod - Datos2.getTiempoProdProd(i) * cantidad;
				
				//No se supere el tiempo de elaboracion maximo semanal  
				t_elab = t_elab - Datos2.getTiempoElabProd(i) * cantidad; 
			}
		}

		return goal - (t_prod >= 0 ? 0 : -t_prod * 1000)
				    - (t_elab >= 0 ? 0 : -t_elab * 1000);
	}

	@Override
	public Solucion2 solution(List<Integer> value) {
		return Solucion2.create(value); 
	}

	
}