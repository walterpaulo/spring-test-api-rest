package br.com.supera.api.services;

import java.util.ArrayList;
import java.util.List;

import br.com.supera.api.models.Product;

public class CarrinhoDeCompra {
	private List<CarrinhoProdutos> listaProdutos;

	public CarrinhoDeCompra() {
		super();
		this.listaProdutos = new ArrayList<CarrinhoProdutos>();
	}

	public List<CarrinhoProdutos> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<CarrinhoProdutos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public void incluirProduto(Product produto, int qtde) {
		getListaProdutos().add(new CarrinhoProdutos(produto, qtde));
	}

	public void removerProdutoPorId(long id) {
		CarrinhoProdutos obj = getListaProdutos().stream().filter(u -> u.getProduto().getId() == id).findFirst().get();
		getListaProdutos().remove(obj);
	}
	

	public int quantidadeProdutos() {
		return getListaProdutos().size();
	}

}
