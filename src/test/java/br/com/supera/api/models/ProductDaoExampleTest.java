package br.com.supera.api.models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Parameter;
import javax.persistence.Query;

import com.github.dbunit.rules.DBUnitRule;
import com.github.dbunit.rules.api.configuration.DBUnit;
import com.github.dbunit.rules.api.dataset.DataSet;
import com.github.dbunit.rules.util.EntityManagerProvider;

import br.com.supera.api.services.CarrinhoDeCompra;
import br.com.supera.api.services.CriadorDeCarrinho;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.github.dbunit.rules.util.EntityManagerProvider.em;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class ProductDaoExampleTest {
	private CarrinhoDeCompra compra;
	private final CriadorDeCarrinho criador = new CriadorDeCarrinho();
	private CarrinhoDeCompra carrinho;

	public CarrinhoDeCompra getCompra() {
		return compra;
	}

	public void setCompra(CarrinhoDeCompra compra) {
		this.compra = compra;
	}

	@Rule
	public EntityManagerProvider emProvider = EntityManagerProvider.instance("productDS");

	@Rule
	public DBUnitRule dbUnitRule = DBUnitRule.instance(emProvider.connection());

	@Before
	public void iniciarCenario() {

		carrinho = criador.constroi();
		setCompra(carrinho);

	}

	@Test
	@DBUnit(allowEmptyFields = true)
	@DataSet("products.yml")
	public void shouldListProducts() {
		try {
			List<Product> products = em().createQuery("select p from Product p").getResultList();
			assertNotNull(products);
			assertEquals(9, products.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	@DBUnit(allowEmptyFields = true)
	@DataSet("products.yml")
	public void procurarProdutoPorId_test() {
		try {

			Product obj = new Product(99L, "Call Of Duty WWII", BigDecimal.valueOf(249.99), Short.parseShort("205"),
					"call-of-duty-wwii.png");
			Product product = (Product) em().createQuery("select p from Product p where p.id = :pid")
					.setParameter("pid", 99L).getSingleResult();

			assertNotNull(product);
			assertEquals(obj.getName(), product.getName());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	@DBUnit(allowEmptyFields = true)
	@DataSet("products.yml")
	public void adicionarProduto_test() {
		try {

			Product obj = new Product(99L, "Call Of Duty WWII", BigDecimal.valueOf(249.99), Short.parseShort("205"),
					"call-of-duty-wwii.png");
			Product product = (Product) em().createQuery("select p from Product p where p.id = :pid")
					.setParameter("pid", 99L).getSingleResult();

			assertNotNull(product);
			assertEquals(obj.getName(), product.getName());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	@DBUnit(allowEmptyFields = true)
	@DataSet("products.yml")
	public void quantidadeItemNoCarrinho_test() {
		try {
			assertNotNull(getCompra());
			assertEquals(0, getCompra().quantidadeProdutos());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	@DBUnit(allowEmptyFields = true)
	@DataSet("products.yml")
	public void adicionarProdutoCarrinho_test() {
		try {
			Product findPro1 = (Product) em().createQuery("select p from Product p where p.id = :pid")
					.setParameter("pid", 12L).getSingleResult();
			Product findPro2 = (Product) em().createQuery("select p from Product p where p.id = :pid")
					.setParameter("pid", 99L).getSingleResult();

			carrinho.incluirProduto(findPro1, 2);
			carrinho.incluirProduto(findPro2, 2);
			assertNotNull(getCompra());
			assertEquals(2, getCompra().quantidadeProdutos());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

}
