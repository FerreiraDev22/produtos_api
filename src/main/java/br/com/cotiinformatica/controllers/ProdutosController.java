package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.ProdutosGetDto;
import br.com.cotiinformatica.dtos.ProdutosPostDto;
import br.com.cotiinformatica.dtos.ProdutosPutDto;
import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.repositories.ProdutoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Controle de Produtos")
@RestController
public class ProdutosController {

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para cadastro de produtos.")
	@PostMapping("/api/produtos")
	public ResponseEntity<String> post(@RequestBody ProdutosPostDto dto) {

		try {
			
			Produto produto = new Produto();
			
			produto.setNome(dto.getNome());
			produto.setDescricao(dto.getDescricao());
			produto.setPreco(dto.getPreco());
			produto.setQuantidade(dto.getQuantidade());
			produto.setDataCadastro(new Date());
			
			ProdutoRepository produtoRepository = new ProdutoRepository();
			produtoRepository.create(produto);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Produto cadastrado com sucesso.");
		}
		catch(Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao cadastrar produto: " + e.getMessage());
		}		
	}

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para atualização de produtos.")
	@PutMapping("/api/produtos")
	public ResponseEntity<String> put(@RequestBody ProdutosPutDto dto) {

		try {
			
			//pesquisar o produto no banco de dados através do ID
			ProdutoRepository produtoRepository = new ProdutoRepository();
			Produto produto = produtoRepository.findById(dto.getIdProduto());
			
			//verificar se o produto não foi encontrado
			if(produto == null) {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Produto não encontrado. Verifique o ID informado.");
			}
			else {
				
				//atualizar os dados do produto
				produto.setNome(dto.getNome());
				produto.setDescricao(dto.getDescricao());
				produto.setPreco(dto.getPreco());
				produto.setQuantidade(dto.getQuantidade());
				
				produtoRepository.update(produto);
				
				return ResponseEntity.status(HttpStatus.OK)
						.body("Produto atualizado com sucesso.");
			}
		}
		catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao atualizar produto: " + e.getMessage());
		}
	}

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para exclusão de produtos.")
	@DeleteMapping("/api/produtos/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
		
		try {
			
			//pesquisar o produto no banco de dados através do ID
			ProdutoRepository produtoRepository = new ProdutoRepository();
			Produto produto = produtoRepository.findById(id);
			
			//verificar se o produto não foi encontrado
			if(produto == null) {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Produto não encontrado. Verifique o ID informado.");
			}
			else {
				
				//excluindo o produto
				produtoRepository.delete(produto);
				
				return ResponseEntity.status(HttpStatus.OK)
						.body("Produto excluído com sucesso.");
			}
		}
		catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao excluir produto: " + e.getMessage());
		}
	}

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para consultar todos os produtos.")
	@GetMapping("/api/produtos")
	public ResponseEntity<List<ProdutosGetDto>> getAll() {

		try {
			
			List<ProdutosGetDto> lista = new ArrayList<ProdutosGetDto>();
			
			//consultando os produtos no banco de dados
			ProdutoRepository produtoRepository = new ProdutoRepository();
			for(Produto produto : produtoRepository.findAll()) {
				
				ProdutosGetDto dto = new ProdutosGetDto();
				
				dto.setIdProduto(produto.getIdProduto());
				dto.setNome(produto.getNome());
				dto.setDescricao(produto.getDescricao());
				dto.setPreco(produto.getPreco());
				dto.setQuantidade(produto.getQuantidade());
				dto.setTotal(produto.getPreco() * produto.getQuantidade());
				dto.setDataCadastro(produto.getDataCadastro());
				
				lista.add(dto);
			}
			
			return ResponseEntity.status(HttpStatus.OK)
					.body(lista);
		}
		catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}

}


















