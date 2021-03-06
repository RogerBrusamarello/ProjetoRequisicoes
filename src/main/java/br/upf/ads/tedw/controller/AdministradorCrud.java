package br.upf.ads.tedw.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import br.upf.ads.tedw.beans.Administrador;
import br.upf.ads.tedw.jpa.JPAUtil;
import br.upf.ads.tedw.jsf.JSFUtil;

@ManagedBean
@ViewScoped
public class AdministradorCrud implements Serializable {

	private static final long serialVersionUID = 1L;
	private Boolean editando;
	private List<Administrador> lista;
	private Administrador selecionado;

	public AdministradorCrud() {
		editando = false;
	}

	public Boolean getEditando() {
		return editando;
	}

	public void setEditando(Boolean editando) {
		this.editando = editando;
	}

	public List<Administrador> getLista() {
		return lista;
	}

	public void setLista(List<Administrador> lista) {
		this.lista = lista;
	}

	public Administrador getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Administrador selecionado) {
		this.selecionado = selecionado;
	}

	@SuppressWarnings("unchecked")
	public void carregarLista() {
		EntityManager em = JPAUtil.getEntityManager();
		lista = em.createQuery("from Administrador order by nome").getResultList();
		em.close();
	}

	public void incluir() {
		editando = true;
		selecionado = new Administrador();
	}

	public void alterar() {
		editando = true;
	}

	public void salvar() {
		try {
			editando = false;
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			em.merge(selecionado);
			em.getTransaction().commit();
			em.close();
			carregarLista();
			JSFUtil.mensagemDeSucessoSalvar();
		} catch (Throwable e) {
			e.printStackTrace();
			JSFUtil.mensagemDeErroSalvar();
		}
	}

	public void excluir() {
		try {
			editando = false;
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(selecionado));
			em.getTransaction().commit();
			em.close();
			carregarLista();
			JSFUtil.mensagemDeSucessoExcluir();
		} catch (Throwable e) {
			e.printStackTrace();
			JSFUtil.mensagemDeErroExcluir();
		}
	}

	public void cancelar() {
		editando = false;
		selecionado = null;
	}
}
