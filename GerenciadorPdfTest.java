package br.com.odontoprev.reajustecliente.gerenciador.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.DocumentException;

import br.com.odontoprev.reajustecliente.GeradorReajuste;
import br.com.odontoprev.reajustecliente.enums.CartaReajusteModeloEnum;
import br.com.odontoprev.reajustecliente.gerenciador.GerenciadorPdf;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.CartaReajusteModelo;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.DadosCalculoReajuste;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.DadosCartaReajuste;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.DadosCelula;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.DadosEmpresa;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.DadosMoeda;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.DadosPlanos;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.ModeloEmpresarialDesconto;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.ModeloEmpresarialIndice;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.ModeloEmpresarialPadrao;
import br.com.odontoprev.reajustecliente.vo.cartareajuste.ModeloPoolPadrao;

public class GerenciadorPdfTest{

	public  Logger logger;
	private GerenciadorPdf gerenciadorPdf;
	

	@Before
	public void init(){		
		this.logger = Logger.getLogger(GeradorReajuste.class);
		this.gerenciadorPdf = new GerenciadorPdf();		
		gerenciadorPdf.setDependencia(logger);
	}
		
	
	@Test
	public void generateFileComSucesso() throws IOException, DocumentException{
		
		CartaReajusteModelo modeloVoEmpresarialPadrao = getModeloEmpresarialPadrao();
		CartaReajusteModelo modeloVoPoolPadrao = getModeloPoolPadrao();
		CartaReajusteModelo modeloVoEmpresarialIndice = getModeloEmpresarialIndice();		
		CartaReajusteModelo modeloVoEmpresarialDesconto = getModeloEmpresarialDesconto();
		
		String srcEmpresarialPadrao = "C://tmp//transparencia//carta-reajuste//modelos//" + "modelo_empresarial_padrao_odontoprev.pdf";
		String srcPoolPadrao = "C://tmp//transparencia//carta-reajuste//modelos//" + "modelo_empresarial_pool_padrao_odontoprev.pdf";
		String srcEmpresarialIndice = "C://tmp//transparencia//carta-reajuste//modelos//" + "modelo_empresarial_indice_odontoprev.pdf";
		String srcEmpresarialDesconto = "C://tmp//transparencia//carta-reajuste//modelos//" + "modelo_empresarial_desconto_odontoprev.pdf";
		
		String srcEmpresarialPadraoP2 = "C://tmp//transparencia//carta-reajuste//modelos//" + "modelo_empresarial_padrao_odontoprev_p2.pdf";
		String srcPoolPadraoP2 = "C://tmp//transparencia//carta-reajuste//modelos//" + "modelo_empresarial_pool_padrao_odontoprev_p2.pdf";
		String srcEmpresarialIndiceP2 = "C://tmp//transparencia//carta-reajuste//modelos//" + "modelo_empresarial_indice_odontoprev_p2.pdf";
		String srcEmpresarialDescontoP2 = "C://tmp//transparencia//carta-reajuste//modelos//" + "modelo_empresarial_desconto_odontoprev_p2.pdf";
		
		String src_logo = "C://tmp//transparencia//carta-reajuste//logos//" + "privian.png";
		
		String destEmpresarialPadrao = "C://tmp//transparencia//carta-reajuste//cartas//" + "carta_reajuste_empresarial_padrao_JUnitTest.pdf";
		String destPoolPadrao = "C://tmp//transparencia//carta-reajuste//cartas//" + "carta_reajuste_pool_padrao_JUnitTest.pdf";
		String destEmpresarialIndice = "C://tmp//transparencia//carta-reajuste//cartas//" + "carta_reajuste_empresarial_indice_JUnitTest.pdf";
		String destEmpresarialDesconto = "C://tmp//transparencia//carta-reajuste//cartas//" + "carta_reajuste_empresarial_desconto_JUnitTest.pdf";
				
		
		
		gerenciadorPdf.generateFile(srcEmpresarialPadrao, srcEmpresarialPadraoP2, src_logo, destEmpresarialPadrao, modeloVoEmpresarialPadrao);
		gerenciadorPdf.generateFile(srcPoolPadrao, srcPoolPadraoP2, src_logo, destPoolPadrao, modeloVoPoolPadrao);
		gerenciadorPdf.generateFile(srcEmpresarialIndice, srcEmpresarialIndiceP2, src_logo, destEmpresarialIndice, modeloVoEmpresarialIndice);
		gerenciadorPdf.generateFile(srcEmpresarialDesconto, srcEmpresarialDescontoP2, src_logo, destEmpresarialDesconto, modeloVoEmpresarialDesconto);
		
	}
	
	private ModeloEmpresarialIndice getModeloEmpresarialIndice(){
		
		
		ModeloEmpresarialIndice modeloEmpresarialIndice = new ModeloEmpresarialIndice();
		modeloEmpresarialIndice.setCartaReajusteModeloEnum(CartaReajusteModeloEnum.MODELO_EMPRESARIAL_INDICE);
		
		DadosCartaReajuste dadosCartaReajuste = new DadosCartaReajuste();
		DadosEmpresa dadosEmpresa = new DadosEmpresa();
		DadosCelula dadosCelula = new DadosCelula();
		DadosCalculoReajuste dadosCalculoReajuste = new DadosCalculoReajuste();
		DadosMoeda dadosMoeda = new DadosMoeda();
		
		dadosCalculoReajuste.setCustoOperacional("R$265,268.94");
		dadosCalculoReajuste.setCustoOperacionalFuturo("R$291,901.95");
		dadosEmpresa.setDataAniversarioReajuste(new Date());
		dadosCartaReajuste.setDataGeracaoCarta(new Date());
		dadosCartaReajuste.setNomeCidade("Barueri");
		dadosCelula.setEmailCelula("odontoprev@odontoprev.com.br");
		dadosCalculoReajuste.setFaturamento("R$409,663.87");
		dadosMoeda.setIndiceOficialMoeda("10.04%");
		dadosCalculoReajuste.setLimiteTecnico("60.00%");
		dadosMoeda.setMoeda("IPC");
		dadosCelula.setNomeCelula("Celula IV");
		dadosEmpresa.setNomeEmpresa("Empresa Teste ODPV Privian");
		dadosCalculoReajuste.setNovoFaturamento("R$486,503.24");
		dadosCalculoReajuste.setPercentualReajuste("28.80");
		
		dadosCelula.setTelefoneCelula("11 9878 8391");
		dadosCartaReajuste.setUltimosDozeMeses("06/2015 - 06/2016");
		
		List<DadosPlanos> listaDadosPlanos = new ArrayList<DadosPlanos>();
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		
		modeloEmpresarialIndice.setDadosPlanosList(listaDadosPlanos);
		modeloEmpresarialIndice.setDadosCartaReajuste(dadosCartaReajuste);
		modeloEmpresarialIndice.setDadosEmpresa(dadosEmpresa);
		modeloEmpresarialIndice.setDadosMoedas(dadosMoeda);
		modeloEmpresarialIndice.setDadosCelula(dadosCelula);
		modeloEmpresarialIndice.setDadosCalculoReajuste(dadosCalculoReajuste);
		modeloEmpresarialIndice.setPercentualReajusteSugeridoRS("28.80");
		
		return modeloEmpresarialIndice;
		
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
		dadosCartaReajuste.setNomeCidade("Barueri");
		dadosCelula.setEmailCelula("odontoprev@odontoprev.com.br");
		dadosCalculoReajuste.setFaturamento("R$409,663.87");
		dadosMoeda.setIndiceOficialMoeda("10.04%");
		dadosCalculoReajuste.setLimiteTecnico("60.00%");
		dadosMoeda.setMoeda("IPC");
		dadosCelula.setNomeCelula("Celula IV");
		dadosEmpresa.setNomeEmpresa("Empresa Teste ODPV Privian");
		dadosCalculoReajuste.setNovoFaturamento("R$486,503.24");
		dadosCalculoReajuste.setPercentualReajuste("28.80");
		dadosCalculoReajuste.setReajusteTecnico("29.03");
		
		dadosCelula.setTelefoneCelula("11 9878 8391");
		dadosCartaReajuste.setUltimosDozeMeses("06/2015 - 06/2016");
		
		List<DadosPlanos> listaDadosPlanos = new ArrayList<DadosPlanos>();
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		
		
		
	}
	
	private ModeloPoolPadrao getModeloPoolPadrao(){
		
		ModeloPoolPadrao modeloPoolPadrao = new ModeloPoolPadrao();
		modeloPoolPadrao.setCartaReajusteModeloEnum(CartaReajusteModeloEnum.MODELO_POOL_PADRAO);
		
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
		dadosCartaReajuste.setNomeCidade("Barueri");
		dadosEmpresa.setNomeEmpresa("Empresa Teste ODPV Privian");
		dadosCalculoReajuste.setNovoFaturamento("R$486,503.24");
		dadosCalculoReajuste.setPercentualReajuste("28.80");
		
		dadosCelula.setTelefoneCelula("11 9878 8391");
		dadosCartaReajuste.setUltimosDozeMeses("06/2015 - 06/2016");
		
		List<DadosPlanos> listaDadosPlanos = new ArrayList<DadosPlanos>();
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());listaDadosPlanos.add(getDadosPlanos());
		listaDadosPlanos.add(getDadosPlanos());
		
		
		
		modeloPoolPadrao.setDadosPlanosList(listaDadosPlanos);
		modeloPoolPadrao.setDadosCartaReajuste(dadosCartaReajuste);
		modeloPoolPadrao.setDadosEmpresa(dadosEmpresa);
		modeloPoolPadrao.setDadosMoedas(dadosMoeda);
		modeloPoolPadrao.setDadosCelula(dadosCelula);
		modeloPoolPadrao.setDadosCalculoReajuste(dadosCalculoReajuste);
		modeloPoolPadrao.setPercentualReajusteSugeridoRS("28.80");
		
		return modeloPoolPadrao;		
	}
	
	private ModeloEmpresarialDesconto getModeloEmpresarialDesconto(){
	
	ModeloEmpresarialDesconto modeloEmpresarialDesconto = new ModeloEmpresarialDesconto();
	modeloEmpresarialDesconto.setCartaReajusteModeloEnum(CartaReajusteModeloEnum.MODELO_EMPRESARIAL_DESCONTO);
	
	DadosCartaReajuste dadosCartaReajuste = new DadosCartaReajuste();
	DadosEmpresa dadosEmpresa = new DadosEmpresa();
	DadosCelula dadosCelula = new DadosCelula();
	DadosCalculoReajuste dadosCalculoReajuste = new DadosCalculoReajuste();
	DadosMoeda dadosMoeda = new DadosMoeda();
	
	dadosCalculoReajuste.setCustoOperacional("R$265,268.94");
	dadosCalculoReajuste.setCustoOperacionalFuturo("R$291,901.95");
	dadosEmpresa.setDataAniversarioReajuste(new Date());
	dadosCartaReajuste.setDataGeracaoCarta(new Date());
	dadosCartaReajuste.setNomeCidade("Barueri");
	dadosCelula.setEmailCelula("odontoprev@odontoprev.com.br");
	dadosCalculoReajuste.setFaturamento("R$409,663.87");
	dadosMoeda.setIndiceOficialMoeda("10.04%");
	dadosCalculoReajuste.setLimiteTecnico("60.00%");
	dadosMoeda.setMoeda("IPC");
	dadosCelula.setNomeCelula("Celula IV");
	dadosEmpresa.setNomeEmpresa("Empresa Teste ODPV Privian");
	dadosCalculoReajuste.setNovoFaturamento("R$486,503.24");
	dadosCalculoReajuste.setPercentualReajuste("28.80");
	dadosCalculoReajuste.setReajusteTecnico("29.03");
	
	dadosCelula.setTelefoneCelula("11 9878 8391");
	dadosCartaReajuste.setUltimosDozeMeses("06/2015 - 06/2016");
	
	List<DadosPlanos> listaDadosPlanos = new ArrayList<DadosPlanos>();
	listaDadosPlanos.add(getDadosPlanos());
	listaDadosPlanos.add(getDadosPlanos());
	listaDadosPlanos.add(getDadosPlanos());
	listaDadosPlanos.add(getDadosPlanos());
	listaDadosPlanos.add(getDadosPlanos());
	listaDadosPlanos.add(getDadosPlanos());
	listaDadosPlanos.add(getDadosPlanos());
	listaDadosPlanos.add(getDadosPlanos());

	modeloEmpresarialDesconto.setDadosPlanosList(listaDadosPlanos);
	modeloEmpresarialDesconto.setDadosCartaReajuste(dadosCartaReajuste);
	modeloEmpresarialDesconto.setDadosEmpresa(dadosEmpresa);
	modeloEmpresarialDesconto.setDadosMoedas(dadosMoeda);
	modeloEmpresarialDesconto.setDadosCelula(dadosCelula);
	modeloEmpresarialDesconto.setDadosCalculoReajuste(dadosCalculoReajuste);
	modeloEmpresarialDesconto.setPercentualReajusteSugeridoRS("28.80");
	
	return modeloEmpresarialDesconto;
	
}

private DadosPlanos getDadosPlanos2(){
		
		DadosPlanos dadosPlanos = new DadosPlanos();
		dadosPlanos.setNomePlano("Master LARD               90 D");
		dadosPlanos.setNovoPrecoPlano("121.79");
		dadosPlanos.setPercentualReajusteSugerido("28.80");
		dadosPlanos.setPrecoPlano("94.56");
		
		return dadosPlanos;
	}
	
private DadosPlanos getDadosPlanos3(){
		
		DadosPlanos dadosPlanos = new DadosPlanos();
		dadosPlanos.setNomePlano("Master LARD               90 D");
		dadosPlanos.setNovoPrecoPlano("121.79");
		dadosPlanos.setPercentualReajusteSugerido("28.80");
		dadosPlanos.setPrecoPlano("94.56");
		
		return dadosPlanos;
	}
	
	
	private DadosPlanos getDadosPlanos(){
		
		DadosPlanos dadosPlanos = new DadosPlanos();
		dadosPlanos.setNomePlano("Master LARD               90 D");
		dadosPlanos.setNovoPrecoPlano("121.79");
		dadosPlanos.setPercentualReajusteSugerido("28.80");
		dadosPlanos.setPrecoPlano("94.56");
		
		return dadosPlanos;
	}
	
	
	
	
	
}
