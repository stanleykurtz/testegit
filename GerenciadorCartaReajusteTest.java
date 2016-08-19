package br.com.odontoprev.reajustecliente.gerenciador.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.odontoprev.reajustecliente.GeradorReajuste;
import br.com.odontoprev.reajustecliente.dao.ReajusteDAO;
import br.com.odontoprev.reajustecliente.enums.CartaReajusteModeloEnum;
import br.com.odontoprev.reajustecliente.enums.TipoSeparadorCalculoReajusteEnum;
import br.com.odontoprev.reajustecliente.gerenciador.GerenciadorCartaReajuste;
import br.com.odontoprev.reajustecliente.gerenciador.GerenciadorLog;
import br.com.odontoprev.reajustecliente.gerenciador.GerenciadorPdf;
import br.com.odontoprev.reajustecliente.gerenciador.GerenciadorReajuste;
import br.com.odontoprev.reajustecliente.gerenciador.GerenciadorRegras;
import br.com.odontoprev.reajustecliente.gerenciador.GerenciadorSeparador;
import br.com.odontoprev.reajustecliente.util.CalendarioUtil;
import br.com.odontoprev.reajustecliente.util.ConnectionManager;
import br.com.odontoprev.reajustecliente.vo.Empresa;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.CartaReajusteModelo;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.DadosCalculoReajuste;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.DadosCartaReajuste;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.DadosCelula;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.DadosEmpresa;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.DadosMoeda;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.DadosPlanos;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.ModeloEmpresarialPadrao;

@RunWith(MockitoJUnitRunner.class)
public class GerenciadorCartaReajusteTest {

	public  Logger logger;
	private ReajusteDAO reajusteDAO;
	private GerenciadorPdf gerenciadorPdf;
	private CalendarioUtil calendarioUtil;
	private GerenciadorRegras gerenciadorRegras; 
	private GerenciadorSeparador gerenciadorSeparador;
	private GerenciadorReajuste gerenciadorReajuste;
	private GerenciadorLog gerenciadorLog;
	
	private static GerenciadorCartaReajuste gerenciadorCartaReajuste = new GerenciadorCartaReajuste();
	
	@Before
	public void init(){		
		this.reajusteDAO = new ReajusteDAO();		
		this.gerenciadorPdf = new GerenciadorPdf();
		this.calendarioUtil = new CalendarioUtil();
		this.gerenciadorRegras = new GerenciadorRegras();
		this.gerenciadorSeparador = new GerenciadorSeparador();
		this.gerenciadorReajuste = new GerenciadorReajuste();
		this.gerenciadorLog = new GerenciadorLog();
		this.logger = Logger.getLogger(GeradorReajuste.class);
		
		gerenciadorSeparador.setDependencia(calendarioUtil, reajusteDAO, logger);
		gerenciadorReajuste.setDependencia(calendarioUtil, reajusteDAO, gerenciadorCartaReajuste, gerenciadorRegras, gerenciadorSeparador, gerenciadorLog, logger);
		gerenciadorRegras.setDependencia(gerenciadorReajuste, calendarioUtil, gerenciadorSeparador, reajusteDAO, logger);
		gerenciadorCartaReajuste.setDependencia(this.reajusteDAO, gerenciadorRegras, gerenciadorPdf, logger);
	}
	
	@Before
	public void initDB(){
		try {
			ConnectionManager.criarConexaoDCMS();
			ConnectionManager.criarConexaoBI();
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
	
	private Empresa getEmpresaDentalCorpEmpresarial(){
		Empresa empresa = new Empresa();
		empresa.setCdEmpresa("000004");
		empresa.setNomeFantasia("Empresa Teste ODPV Privian");
		empresa.setCdGrupoEmpresa("000002");
		empresa.setMesAniversarioReajuste("4");
		empresa.setCdMarca(30);
		empresa.setQtdeVidas(700L);
		empresa.getClausuaReajusteDiferenciado().setCodigo("7");
		
		return empresa;
	}
	
	private ModeloEmpresarialPadrao getModeloEmpresarialPadrao(){
		
		ModeloEmpresarialPadrao modeloEmpresarialPadrao = new ModeloEmpresarialPadrao();
		modeloEmpresarialPadrao.setCartaReajusteModeloEnum(CartaReajusteModeloEnum.MODELO_EMPRESARIAL_PADRAO);
		
		DadosCartaReajuste dadosCartaReajuste = new DadosCartaReajuste();
		DadosEmpresa dadosEmpresa = new DadosEmpresa();
		DadosCelula dadosCelula = new DadosCelula();
		DadosCalculoReajuste dadosCalculoReajuste = new DadosCalculoReajuste();
		DadosMoeda dadosMoeda = new DadosMoeda();
		
		dadosCalculoReajuste.setCustoOperacional("R$265,268.94");
		dadosCalculoReajuste.setCustoOperacionalFuturo("R$291,901.95");
		dadosEmpresa.setDataAniversarioReajuste(new Date());
		dadosCartaReajuste.setDataGeracaoCarta(new Date());
		dadosCelula.setEmailCelula("odontoprev@odontoprev.com.br");
		dadosCalculoReajuste.setFaturamento("R$409,663.87");
		dadosMoeda.setIndiceOficialMoeda("10.04%");
		dadosCalculoReajuste.setLimiteTecnico("60.00%");
		dadosMoeda.setMoeda("IPC");
		dadosCelula.setNomeCelula("Celula IV");
		dadosEmpresa.setNomeEmpresa("Empresa Teste ODPV Privian");
		dadosCalculoReajuste.setNovoFaturamento("R$486,503.24");
		
		dadosCelula.setTelefoneCelula("11 9878 8391");
		dadosCartaReajuste.setUltimosDozeMeses("06/2015 - 06/2015");
		
		List<DadosPlanos> listaDadosPlanos = new ArrayList<DadosPlanos>();
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		
		modeloEmpresarialPadrao.setDadosPlanosList(listaDadosPlanos);
		modeloEmpresarialPadrao.setDadosCartaReajuste(dadosCartaReajuste);
		modeloEmpresarialPadrao.setDadosEmpresa(dadosEmpresa);
		modeloEmpresarialPadrao.setDadosMoedas(dadosMoeda);
		modeloEmpresarialPadrao.setDadosCelula(dadosCelula);
		modeloEmpresarialPadrao.setDadosCalculoReajuste(dadosCalculoReajuste);
		modeloEmpresarialPadrao.setPercentualReajusteSugeridoRS("28.80%");
		
		return modeloEmpresarialPadrao;
		
	}
	
	private CartaReajusteModelo getModeloEmpresarialIndice(){
		
		//ModeloEmpresarialIndice modeloEmpresarialIndice = new ModeloEmpresarialIndice();
		CartaReajusteModelo modeloEmpresarialIndice = new CartaReajusteModelo();
		//modeloEmpresarialIndice.setCartaReajusteModeloEnum(CartaReajusteModeloEnum.MODELO_EMPRESARIAL_INDICE);
		//Teste
		
		DadosCartaReajuste dadosCartaReajuste = new DadosCartaReajuste();
		DadosEmpresa dadosEmpresa = new DadosEmpresa();
		DadosCelula dadosCelula = new DadosCelula();
		DadosMoeda dadosMoeda = new DadosMoeda();
		
		dadosEmpresa.setDataAniversarioReajuste(new Date());
		dadosCartaReajuste.setDataGeracaoCarta(new Date());
		dadosCelula.setEmailCelula("odontoprev@odontoprev.com.br");		
		dadosMoeda.setIndiceOficialMoeda("10.04%");		
		dadosMoeda.setMoeda("IPC");
		dadosCelula.setNomeCelula("Celula IV");
		dadosEmpresa.setNomeEmpresa("Empresa Teste ODPV Privian");		
		dadosCelula.setTelefoneCelula("11 9878 8391");
		dadosCartaReajuste.setUltimosDozeMeses("06/2015 - 06/2015");
		
		List<DadosPlanos> listaDadosPlanos = new ArrayList<DadosPlanos>();
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		
		modeloEmpresarialIndice.setListDadosPlanos(listaDadosPlanos);
		modeloEmpresarialIndice.setDadosCartaReajuste(dadosCartaReajuste);
		modeloEmpresarialIndice.setDadosEmpresa(dadosEmpresa);
		modeloEmpresarialIndice.setDadosMoeda(dadosMoeda);
		modeloEmpresarialIndice.setDadosCelula(dadosCelula);
		
		
		return modeloEmpresarialIndice;
		
	}
	
	
	private DadosPlanos getDadosPlanos(){
		
		DadosPlanos dadosPlanos = new DadosPlanos();
		dadosPlanos.setNomePlano("Master LARD               90 D");
		dadosPlanos.setNovoPrecoPlano("121.79");
		dadosPlanos.setPercentualReajusteSugerido("28.80%");
		dadosPlanos.setPrecoPlano("94.56");
		
		return dadosPlanos;
	}
	
	
	@Test
	public void gerarCartaReajusteEmpresarialPrivianSucesso(){
		
		Empresa empresa = getEmpresaDentalCorpEmpresarial();
		CartaReajusteModelo modeloVo = getModeloEmpresarialIndice();
		TipoSeparadorCalculoReajusteEnum tipoCalculo = TipoSeparadorCalculoReajusteEnum.EMPRESARIAL;
		
		boolean result = gerenciadorCartaReajuste.gerarCartaReajuste(empresa, modeloVo, tipoCalculo);		
		assertTrue(result);
		
	}
	
	@Test
	public void gerarPdfPadraoComSucesso(){
		
		Empresa empresa = getEmpresaDentalCorpEmpresarial();
		CartaReajusteModelo modeloVo = getModeloEmpresarialPadrao();
		
		TipoSeparadorCalculoReajusteEnum tipoCalculo = TipoSeparadorCalculoReajusteEnum.EMPRESARIAL;
		
		boolean result = gerenciadorCartaReajuste.gerarCartaReajuste(empresa, modeloVo, tipoCalculo);
		assertTrue(result);
		
		
	}
	
}
