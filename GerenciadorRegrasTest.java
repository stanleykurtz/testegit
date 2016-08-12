package br.com.odontoprev.reajustecliente.gerenciador.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.odontoprev.reajustecliente.GeradorReajuste;
import br.com.odontoprev.reajustecliente.dao.ReajusteDAO;
import br.com.odontoprev.reajustecliente.gerenciador.GerenciadorReajuste;
import br.com.odontoprev.reajustecliente.gerenciador.GerenciadorRegras;
import br.com.odontoprev.reajustecliente.gerenciador.GerenciadorSeparador;
import br.com.odontoprev.reajustecliente.util.CalendarioUtil;
import br.com.odontoprev.reajustecliente.vo.Empresa;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.DadosPlanos;

@RunWith(MockitoJUnitRunner.class)
public class GerenciadorRegrasTest {

	@InjectMocks
	private GerenciadorRegras gerenciadorRegras;
	
	@Mock
	private static GerenciadorReajuste gerenciadorReajuste;
	
	@Mock
	private static GerenciadorSeparador gerenciadorSeparador;
	
	
	private static CalendarioUtil calendarioUtil;
	
	@Mock
	private static ReajusteDAO reajusteDAO;
	
	private static Logger logger = Logger.getLogger(GeradorReajuste.class);
	
	@Before
	public void init() {		
		calendarioUtil = new CalendarioUtil();
		gerenciadorRegras.setDependencia(gerenciadorReajuste, calendarioUtil, gerenciadorSeparador, reajusteDAO,  logger);
		
	}
	
	@Test
	public void validaRegraExclusaoPorIdadeMinimaComSucesso() throws Exception{
		
		Empresa empresaValida = obterMockedEmpresa(2016, 2, 1);
		Empresa empresaInvalida = obterMockedEmpresa(2016, 5, 20);
		when(gerenciadorReajuste.obterIdadeMinimaPool()).thenReturn(6);
		
		boolean retornoEmpresaValida = gerenciadorRegras.validaRegraExclusaoPorIdadeMinima(empresaValida);
		boolean retornoEmpresaInvalida = gerenciadorRegras.validaRegraExclusaoPorIdadeMinima(empresaInvalida);
		assertEquals(true, retornoEmpresaValida);
		assertEquals(false, retornoEmpresaInvalida);
		
	}
	
	
	@Test
	public void validaRegraExclusaoPorIdadeMinimaReajusteComSucesso() throws Exception{
		
		Empresa empresaValida = obterMockedEmpresa(2015, 8, 1);
		Empresa empresaInvalida = obterMockedEmpresa(2015, 12, 25);
		when(gerenciadorReajuste.obterIdadeMinimaReajuste()).thenReturn(12);
		
		boolean retornoEmpresaValida = gerenciadorRegras.validaRegraExclusaoPorIdadeMinimaReajuste(empresaValida);
		boolean retornoEmpresaInvalida = gerenciadorRegras.validaRegraExclusaoPorIdadeMinimaReajuste(empresaInvalida);
		assertEquals(true, retornoEmpresaValida);
		assertEquals(false, retornoEmpresaInvalida);
		
	}
	
	@Test
	public void validaRegraExclusaoPorDataUltimoReajusteComSucesso() throws Exception{
		
		when(reajusteDAO.obterPrazoMinimoNovaAberturaChamado()).thenReturn(12);
		
		Empresa empresaValida = obterMockedEmpresa(2015, 2, 14);
		Empresa empresaInvalida = obterMockedEmpresa(2016, 3, 25 );
		
		boolean retornoEmpresaValida = gerenciadorRegras.validaDataUltimoReajuste(empresaValida);
		boolean retornoEmpresaInvalida = gerenciadorRegras.validaDataUltimoReajuste(empresaInvalida);
		
		assertEquals(true, retornoEmpresaValida);
		assertEquals(false, retornoEmpresaInvalida);
		
	}
	
	@Test
	public void formataListDadosPlanosOrdenaComSucesso(){
		
		List<DadosPlanos> listaDadosPlanos = new ArrayList<DadosPlanos>();
		listaDadosPlanos.add(getDadosPlanos("Plano C", "94.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano A", "94.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano D", "94.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano B", "94.56"));
		
		
		gerenciadorRegras.formataListDadosPlanos(listaDadosPlanos);
		
		for(DadosPlanos plano : listaDadosPlanos){
			System.out.println(plano.getNomePlano());
		}
		
		
	}
	
	@Test
	public void formataListDadosPlanosRemovePlanosPrecoEmBrancoComSucesso(){
		
		List<DadosPlanos> listaDadosPlanos = new ArrayList<DadosPlanos>();
		
		DadosPlanos planoA = getDadosPlanos("Plano A", "94.56");
		DadosPlanos planoB = getDadosPlanos("Plano B", "94.56");
		DadosPlanos planoC = getDadosPlanos("Plano C", null);
		DadosPlanos planoD = getDadosPlanos("Plano D", null);
				
		listaDadosPlanos.add(planoC);
		listaDadosPlanos.add(planoA);
		listaDadosPlanos.add(planoD);
		listaDadosPlanos.add(planoB);
		
		gerenciadorRegras.formataListDadosPlanos(listaDadosPlanos);
		
		assertEquals(true, listaDadosPlanos.contains(planoA));
		assertEquals(true, listaDadosPlanos.contains(planoB));
		assertEquals(false, listaDadosPlanos.contains(planoC));
		assertEquals(false, listaDadosPlanos.contains(planoD));
		
	}
	
	
	@Test
	public void formataListDadosPlanosRemovePlanosDuplicadosComSucesso(){
		
		List<DadosPlanos> listaDadosPlanos = new ArrayList<DadosPlanos>();
				
		listaDadosPlanos.add(getDadosPlanos("Plano A   ", "94.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano A ", "94.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano C", "94.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano D", "94.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano A", "102.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano E", "102.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano B", "102.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano E", "102.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano J", "102.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano J  ", "102.56"));
		
		int sizeInicial = listaDadosPlanos.size();
		System.out.println("Size Lista Inicial: " + sizeInicial);
		
		gerenciadorRegras.formataListDadosPlanos(listaDadosPlanos);
		
		int sizeFinal = listaDadosPlanos.size();
		System.out.println("Size Lista Final: " + sizeFinal);
		System.out.println("Planos excluidos: " + (sizeInicial - sizeFinal ));
		
		String tmp = "Plano A   ";
		System.out.println(tmp);
		System.out.println(tmp.trim());
		
//		assertEquals(true, listaDadosPlanos.contains(planoC));
//		assertEquals(true, listaDadosPlanos.contains(planoD));
//		assertEquals(true, listaDadosPlanos.contains(planoA));
//		assertEquals(false, listaDadosPlanos.contains(planoB));
		
	}
	
	
	@Test
	public void formataListDadosPlanosOrdenaPlanosPorPrecoPlanoComSucesso(){
		
		List<DadosPlanos> listaDadosPlanos = new ArrayList<DadosPlanos>();
				
		listaDadosPlanos.add(getDadosPlanos("Plano A   ", "94.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano A ", "240.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano C", "94.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano D", "94.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano A", "102.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano E", "102.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano A", "402.56"));
		listaDadosPlanos.add(getDadosPlanos("Plano E", "602.56"));
		
		Collections.sort(listaDadosPlanos, new DadosPlanos.SortByPrecoPlano());
		
		List<DadosPlanos> planosValidos = new ArrayList<DadosPlanos>();
		for(DadosPlanos plano : listaDadosPlanos){
			
			if(plano.getPrecoPlano() == null){
				continue;
			}
						
			if( !(planosValidos.contains(plano)) ){				
				planosValidos.add(plano);
			}
									
		}
		
		
		System.out.println("** TESTE PLANOS ORDENADOS POR VALOR **");
		for(DadosPlanos plano : planosValidos){
			System.out.println(plano.getNomePlano() + ", " + plano.getPrecoPlano());
		}
		
		
		
	}
	
	
	private DadosPlanos getDadosPlanos(String nomePlano, String precoPlano){
		
		DadosPlanos dadosPlanos = new DadosPlanos();
		//dadosPlanos.setNomePlano("Master LARD               90 D");
		dadosPlanos.setNomePlano(nomePlano);
		dadosPlanos.setNovoPrecoPlano("121.79");
		dadosPlanos.setPercentualReajusteSugerido("28.80");
		dadosPlanos.setPrecoPlano(precoPlano);
		
		return dadosPlanos;
	}
	
	
	@SuppressWarnings("deprecation")
	private Empresa obterMockedEmpresa(int year, int month, int day){
		
		Empresa empresa = mock(Empresa.class);
		when(empresa.getDtIniContrato()).thenReturn(new Date(year - 1900, month -1, day));	
		when(empresa.getDtUltReajuste()).thenReturn(new Date(year - 1900, month -1, day));
		when(empresa.getNomeFantasia()).thenReturn("MOCKED EMPRESA TESTE LTDA");
		when(empresa.getCdEmpresa()).thenReturn("000055");
		
		return empresa;
	}
	
	
}

