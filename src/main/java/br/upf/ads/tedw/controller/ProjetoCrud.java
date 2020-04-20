package br.upf.ads.tedw.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import br.upf.ads.tedw.beans.Cliente;
import br.upf.ads.tedw.beans.Projeto;
import br.upf.ads.tedw.beans.Usuario;
import br.upf.ads.tedw.jpa.JPAUtil;
import br.upf.ads.tedw.jsf.JSFUtil;

@ManagedBean
@ViewScoped
public class ProjetoCrud implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Boolean editando;
	private List<Projeto> lista;
	private Projeto selecionado;
	private List<Usuario> usuarios;
	private List<Cliente> clientes;
	
	public ProjetoCrud() {
		editando = false;
	}
	
	public Boolean getEditando() {
		return editando;
	}
	
	public void setEditando(Boolean editando) {
		this.editando = editando;
	}
	
	public List<Projeto> getLista() {
		return lista;
	}
	
	public void setLista (List<Projeto> lista) {
		this.lista = lista;
	}
	
	public Projeto getSelecionado() {
		return selecionado;
	}
	
	public void setSelecionado (Projeto selecionado) {
		this.selecionado = selecionado;
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	@SuppressWarnings({ "unchecked"})
	public void carregarLista() {
		EntityManager em = JPAUtil.getEntityManager();
		lista = em.createQuery("from Projeto order by nome").getResultList();
		usuarios = em.createQuery("from Usuario order by nome").getResultList();
		clientes = em.createQuery("from Cliente order by nome").getResultList();
		em.close();
	}
	
	public void incluir() {
		editando = true;
		selecionado = new Projeto();
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
		} catch (Throwable e) {
			e.printStackTrace();
			JSFUtil.messagemDeErro("Ocorreu um erro ao salvar os dados.");
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
		} catch (Throwable e) {
			e.printStackTrace();
			JSFUtil.messagemDeErro("Ocorreu um erro ao remover os dados");
		}
	}
	
	public void cancelar() {
		editando = false;
		selecionado = null;
	}

}
