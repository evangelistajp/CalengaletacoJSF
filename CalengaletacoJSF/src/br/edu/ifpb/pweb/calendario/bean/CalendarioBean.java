package br.edu.ifpb.pweb.calendario.bean;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.edu.ifpb.pweb.calendario.model.Anotacao;
import br.edu.ifpb.pweb.calendario.model.Calendario;
import br.edu.ifpb.pweb.calendario.model.Feriado;
import br.edu.ifpb.pweb.calendario.model.FeriadoSubstituto;
import br.edu.ifpb.pweb.calendario.model.Usuario;

@ManagedBean(name="calendarioBean")
@SessionScoped
public class CalendarioBean {
	private Integer dia, mes, ano;
	private Date dataNow = new Date();
	private Date dataEscolhida = new Date();
	private Usuario usuario;
	private String strFeriados;
	private String strAnotacoes;
	private List<Calendario> calendar = new ArrayList<Calendario>();
	@ManagedProperty(value="#{feriadoBean}")
	private FeriadoBean feriadoBean;
	@ManagedProperty(value="#{anotacaoBean}")
	private AnotacaoBean anotacaoBean;
	@ManagedProperty(value ="#{usuarioBean}")
	private UsuarioBean usuarioBean;
	
	

	@PostConstruct
	public void init(){
		dataNow.setTime(dataNow.getTime());
		this.carregaCalendario(dataNow);
		dataEscolhida = dataNow;
	}
	
	public void carregaCalendario(Date data){
		Date[] datas = new Date[50];
		Integer anos;
		calendar.clear();
		Calendar c =  Calendar.getInstance();
		c.setTime(data);
		
		for (int i = c.getActualMinimum(c.DAY_OF_MONTH); i <= c.getActualMaximum(c.DAY_OF_MONTH ); i++) {
			this.dia = i;
			this.mes = c.get(Calendar.MONTH);
			this.ano = c.get(Calendar.YEAR);
			Calendario cal = new Calendario(dia,mes +1,ano);
			calendar.add(cal);
			List<Anotacao> a = existeAnotacao(cal);
			if (a != null) {
				cal.addAnotacoes(a);
			}
			List<Feriado> f = existeFeriado(cal);
			if(f != null){
				for (int j = 0; j < f.size(); j++) {
					if (f.get(j).getFixo()) {
						List<FeriadoSubstituto> sub = null; /////zdjsahdt
						sub = f.get(j).getSubstitutos();
						if (sub.size() != 0) {
							FeriadoSubstituto sub2 = new FeriadoSubstituto();
							for (int x = c.getActualMinimum(c.DAY_OF_MONTH); x <= c.getActualMaximum(c.DAY_OF_MONTH ); x++) {
								this.dia = x;
								this.mes = c.get(Calendar.MONTH);
								this.ano = c.get(Calendar.YEAR);
								//Calendario cal2 = new Calendario(dia,mes +1,ano);
								cal = new Calendario(dia,mes +1,ano);
								sub2 = existeFeriadoSubstituto(f.get(j), cal); 
								if (sub2 != null) {
									cal.addSubstitutos(sub2);
									calendar.add(cal);
								}
							}
						}else{
							cal.addFeriado(f.get(j));
						}
					}else{
						cal.addFeriado(f.get(j));
					}
			}	
			
		}
		}		
	}
	
	public List<Feriado> existeFeriado(Calendario calendar){
		List<Feriado> feriadosDia = new ArrayList<Feriado>();
		List<Feriado> feriados = new ArrayList<Feriado>();
		feriados = feriadoBean.getFeriados();
		if(feriados!=null){
			for (Feriado feriado : feriados) {
				
				if(feriado.getFixo()==true){
					Format formatfixo = new SimpleDateFormat("dd/MM");
					if (formatfixo.format(feriado.getData()).
							equals(calendar.getDataFixo())) {
						feriadosDia.add(feriado);
					}
				}else{
					Format format = new SimpleDateFormat("dd/MM/yyyy");
					if (format.format(feriado.getData()).
							equals(calendar.getData())) {
						feriadosDia.add(feriado);
					}
				}
			}
			return feriadosDia;
		}
		return null;
	}
	
	public FeriadoSubstituto existeFeriadoSubstituto(Feriado feriado, Calendario calendar){
		List<FeriadoSubstituto> sub = new ArrayList<FeriadoSubstituto>();
		sub = feriado.getSubstitutos();
		for (FeriadoSubstituto feriadoSubstituto : sub) {
			Format format = new SimpleDateFormat("dd/MM/yyyy");
			if (format.format(feriadoSubstituto.getFeriadoFixo().getData())
					.equals(format.format(feriado.getData()))) {
				if (format.format(feriadoSubstituto.getData()).equals(
						calendar.getData())) {
					return feriadoSubstituto;
				}
			}
		}
		return null;
	}
	
	
	
	public List<Anotacao> existeAnotacao(Calendario calendar){
		List<Anotacao> anotacoesDia = new ArrayList<Anotacao>();
		List<Anotacao> anotacoes = new ArrayList<Anotacao>();
		this.usuario = usuarioBean.getUsuarioLogado();
		if (usuario != null) {
			anotacoes = anotacaoBean.pesquisaRetornaAnotacoesDoUsuario(usuario);
			if (anotacoes != null) {
				Format format = new SimpleDateFormat("dd/MM/yyyy");
				for (Anotacao anotacao : anotacoes) {
					if (format.format(anotacao.getData()).equals(calendar.getData())) {
						anotacoesDia.add(anotacao);
					}
				}
			}
		}
		return anotacoesDia;
	}

	
	public void proximoMes(){
		Calendar c = Calendar.getInstance();
		c.setTime(dataEscolhida);
		c.set(c.MONTH, c.get(c.MONTH)+1);
		dataEscolhida = c.getTime();
		this.carregaCalendario(c.getTime());
		
	}
	
	public void proximoAno(){
		Calendar c = Calendar.getInstance();
		c.setTime(dataEscolhida);
		c.set(c.YEAR, c.get(c.YEAR)+1);
		dataEscolhida = c.getTime();
		this.carregaCalendario(c.getTime());
		
	}
	
	public void anteriorMes(){
		Calendar c = Calendar.getInstance();
		c.setTime(dataEscolhida);
		c.set(c.MONTH, c.get(c.MONTH)-1);
		dataEscolhida = c.getTime();
		this.carregaCalendario(dataEscolhida);
	}
	
	public void anteriorAno(){
		Calendar c = Calendar.getInstance();
		c.setTime(dataEscolhida);
		c.set(c.YEAR, c.get(c.YEAR)-1);
		dataEscolhida = c.getTime();
		this.carregaCalendario(dataEscolhida);
	}
	
	
	public Integer getDia() {
		return dia;
	}
	public void setDia(Integer dia) {
		this.dia = dia;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public Date getDataNow() {
		return dataNow;
	}
	public void setDataNow(Date data) {
		this.dataNow = data;
	}

	public List<Calendario> getCalendar() {
		return calendar;
	}

	public void setCalendar(List<Calendario> calendar) {
		this.calendar = calendar;
	}

	public Date getDataEscolhida() {
		return dataEscolhida;
	}

	public void setDataEscolhida(Date dataEscolhida) {
		this.dataEscolhida = dataEscolhida;
	}
	
	public FeriadoBean getFeriadoBean() {
		return feriadoBean;
	}

	public void setFeriadoBean(FeriadoBean feriadoBean) {
		this.feriadoBean = feriadoBean;
	}

	public String getStrFeriados() {
		return strFeriados;
	}

	public void setStrFeriados(String strFeriados) {
		this.strFeriados = strFeriados;
	}

	public String getStrAnotacoes() {
		return strAnotacoes;
	}

	public void setStrAnotacoes(String strAnotacoes) {
		this.strAnotacoes = strAnotacoes;
	}

	public AnotacaoBean getAnotacaoBean() {
		return anotacaoBean;
	}

	public void setAnotacaoBean(AnotacaoBean anotacaoBean) {
		this.anotacaoBean = anotacaoBean;
	}

	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
}
