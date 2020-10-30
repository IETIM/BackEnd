package edu.eci.ieti.ProjectIeti;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.ieti.ProjectIeti.model.*;
import edu.eci.ieti.ProjectIeti.persistence.RoleRepository;
import edu.eci.ieti.ProjectIeti.persistence.ShopRepository;
import edu.eci.ieti.ProjectIeti.persistence.UserRepository;
import edu.eci.ieti.ProjectIeti.security.JwtRequest;
import edu.eci.ieti.ProjectIeti.security.JwtResponse;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProjectIetiApplicationTests {

	@Autowired
	private MockMvc mock;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ShopRepository shopRepository;

	@Autowired
	ObjectMapper objectMapper;

	private Role[] roles = null;

	private String token;

	public void createRoles(){
		roleRepository.deleteAll();
		userRepository.deleteAll();
		shopRepository.deleteAll();
			Role role1 = new Role();
			role1.setRole(ERole.ROLE_USER);
			role1 = roleRepository.save(role1);
			Role role2 = new Role();
			role2.setRole(ERole.ROLE_TENDERO);
			role2 = roleRepository.save(role2);
			Role role3 = new Role();
			role3.setRole(ERole.ROLE_ADMIN);
			role3 = roleRepository.save(role3);
			this.roles =  new Role[]{role1, role2, role3};
	}

	@Test
	public void shoulBeAddANewUser() throws Exception{
		createRoles();
		mock.perform(post("/register").content("{ \"name\": \"dan\",     \"email\": \"dan@mail.com\",     \"password\": \"dan\",     \"cellphone\": \"112323\",     \"address\": \"1\",     \"authorities\":[ {\"role\" : \"ROLE_TENDERO\" }] }")
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
	}

	@Test
	public void shouldntBeAddedUsersWithTheSameEmail() throws Exception{
		createRoles();
		mock.perform(post("/register").content("{ \"name\": \"can\",     \"email\": \"cam@mail.com\",     \"password\": \"cam\",     \"cellphone\": \"112323\",     \"address\": \"1\",     \"authorities\":[ {\"role\" : \"ROLE_TENDERO\" }] }")
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		User user2 = new User("cam@mail.com","C cam","12345","CR 1RA","111112");
		mock.perform(post("/register").content("{ \"name\": \"dan\",     \"email\": \"cam@mail.com\",     \"password\": \"dan\",     \"cellphone\": \"112323\",     \"address\": \"1\",     \"authorities\":[ {\"role\" : \"ROLE_TENDERO\" }] }")
				.header("Content-Type","application/json")).andExpect(status().is4xxClientError());
	}

	@Test
	public void shouldBeGetTheRole() throws Exception{
		createRoles();
		mock.perform(post("/register").content("{ \"name\": \"daniel\",\"email\": \"daniel@mail.com\",\"password\": \"daniel123\",     \"cellphone\": \"112323\", \"address\": \"1\", \"authorities\":[ {\"role\" : \"ROLE_USER\" }] }")
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		JwtRequest req = new JwtRequest("daniel@mail.com","daniel123");
		User user = userRepository.getUserByEmail("daniel@mail.com").get();
		mock.perform(post("/login").content(mapper.writeValueAsString(req))
				.header("Content-Type","application/json")).andDo((request)->{
					System.out.println(request.getResponse().getContentAsString());
					token = mapper.readValue(request.getResponse().getContentAsString(), JwtResponse.class).getToken();

		});
		mock.perform(get("/role").header("Authorization",token)).andDo((request)->{
			String[] rol = mapper.readValue(request.getResponse().getContentAsString(),String[].class);
			assertEquals(rol[0],"ROLE_USER");
		});
	}

	@Test
	public void shouldBeGiveTheUserName() throws Exception{
		createRoles();
		JwtRequest req = new JwtRequest("charlie@mail.com","daniel");
		mock.perform(post("/register").content("{ \"name\": \"dan\",     \"email\": \"charlie@mail.com\",     \"password\": \"daniel\",     \"cellphone\": \"112323\",     \"address\": \"1\",     \"authorities\":[ {\"role\" : \"ROLE_TENDERO\" }] }")
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		mock.perform(post("/storekeeper/register").content("{\"email\":\"charlie@mail.com\",\"password\":\"daniel\",\"name\":\"Charlie\",\"cellphone\":\"121321\",\"address\":\"Data\",\"shop\":{\"name\":\"Test1\"}}")
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		mock.perform(post("/login").content(mapper.writeValueAsString(req))
				.header("Content-Type","application/json")).andDo((request)->{
			token = mapper.readValue(request.getResponse().getContentAsString(), JwtResponse.class).getToken();
		});
		mock.perform(get("/username").header("Authorization",token))
				.andExpect(content().string("charlie@mail.com"));
	}

	@Test
	public void shouldBeAddAShop() throws Exception{
		createRoles();
		JwtRequest req = new JwtRequest("shop1@mail.com","shops");
		mock.perform(post("/register").content("{ \"name\": \"dan\",     \"email\": \"shop1@mail.com\",     \"password\": \"shops\",     \"cellphone\": \"112323\",     \"address\": \"1\",     \"authorities\":[ {\"role\" : \"ROLE_TENDERO\" }] }")
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		mock.perform(post("/login").content(mapper.writeValueAsString(req))
				.header("Content-Type","application/json")).andDo((request)->{
			token = mapper.readValue(request.getResponse().getContentAsString(), JwtResponse.class).getToken();
		});
		Shop shop = new Shop("Shop1",new ArrayList<Product>(),"Calle 10ma Bogota DC","Supermarket");
		mock.perform(post("/shops").header("Authorization",token).content(mapper.writeValueAsString(shop)).contentType("application/json"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void shoudntBeAddAShop() throws Exception{
		createRoles();
		JwtRequest req = new JwtRequest("shop2@mail.com","shops2");
		mock.perform(post("/register").content("{ \"name\": \"shop2\",     \"email\": \"shop2@mail.com\",     \"password\": \"shops2\",     \"cellphone\": \"112323\",     \"address\": \"1\",     \"authorities\":[ {\"role\" : \"ROLE_TENDERO\" }] }")
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		mock.perform(post("/login").content(mapper.writeValueAsString(req))
				.header("Content-Type","application/json")).andDo((request)->{
			token = mapper.readValue(request.getResponse().getContentAsString(), JwtResponse.class).getToken();
		});
		Shop shop = new Shop("Shop2",new ArrayList<Product>(),"Calle 10ma Bogota DC","Supermarket");
		mock.perform(post("/shops").header("Authorization",token).content(mapper.writeValueAsString(shop)).contentType("application/json"))
				.andExpect(status().is2xxSuccessful());
		mock.perform(post("/shops").header("Authorization",token).content(mapper.writeValueAsString(shop)).contentType("application/json"))
				.andExpect(status().is4xxClientError());
	}
	@Test
	public void shouldBeQueryAShop() throws Exception{

		createRoles();
		JwtRequest req = new JwtRequest("shopquery@mail.com","shopsquery");
		mock.perform(post("/register").content("{ \"name\": \"dan\",     \"email\": \"shopquery@mail.com\",     \"password\": \"shopsquery\",     \"cellphone\": \"112323\",     \"address\": \"1\",     \"authorities\":[ {\"role\" : \"ROLE_TENDERO\" }] }")
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		mock.perform(post("/login").content(mapper.writeValueAsString(req))
				.header("Content-Type","application/json")).andDo((request)->{
			token = mapper.readValue(request.getResponse().getContentAsString(), JwtResponse.class).getToken();
		});
		Shop shop = new Shop("shop4",new ArrayList<Product>(),"Calle 5ta Bogota DC","Supermarket");
		MvcResult mvc = mock.perform(post("/shops").header("Authorization",token).content(mapper.writeValueAsString(shop)).contentType("application/json"))
				.andExpect(status().is2xxSuccessful()).andReturn();
		String response = mvc.getResponse().getContentAsString();
		Shop s = objectMapper.readValue(response,Shop.class);
		mock.perform(get("/shops/" + s.getId())).andExpect(status().is2xxSuccessful());
	}

	@Test
	public void shouldntBeQueryUnexistShop() throws Exception{
		mock.perform(get("/shops/unexistShop")).andExpect(status().is4xxClientError());
	}

	@Test
	public void shouldBeFindAshopByType() throws Exception{
		createRoles();
		JwtRequest req = new JwtRequest("shoptype@mail.com","shopstype");
		mock.perform(post("/register").content("{ \"name\": \"dan\",     \"email\": \"shoptype@mail.com\",     \"password\": \"shopstype\",     \"cellphone\": \"112323\",     \"address\": \"1\",     \"authorities\":[ {\"role\" : \"ROLE_TENDERO\" }] }")
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		mock.perform(post("/login").content(mapper.writeValueAsString(req))
				.header("Content-Type","application/json")).andDo((request)->{
			token = mapper.readValue(request.getResponse().getContentAsString(), JwtResponse.class).getToken();
		});
		Shop shop = new Shop("shoptype",new ArrayList<Product>(),"Av Pradilla Chia","generictypeapp");
		mock.perform(post("/shops").header("Authorization",token).content(mapper.writeValueAsString(shop)).contentType("application/json"))
				.andExpect(status().is2xxSuccessful());
		mock.perform(get("/shops?type=generictypeapp")).andDo((response)->{
			Shop[] shops = mapper.readValue(response.getResponse().getContentAsString(),Shop[].class);
			assertEquals(shops.length,1);
			assertEquals(shops[0].getType(),"generictypeapp");
			assertEquals(shops[0].getName(),"shoptype");
		});
	}
	@Test
	public void shouldntBeAddAProductInUnexistStore() throws Exception{
		createRoles();
		User user = new User("shop5@mail.com","shop5","shops5","CR 1RA","111112");
		ArrayList<Role> rolesUser = new ArrayList<>();
		rolesUser.add(roles[1]);
		user.setAuthorities(rolesUser);
		JwtRequest req = new JwtRequest("shop5@mail.com","shops5");
		mock.perform(post("/register").content(mapper.writeValueAsString(user))
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		mock.perform(post("/login").content(mapper.writeValueAsString(req))
				.header("Content-Type","application/json")).andDo((request)->{
			token = mapper.readValue(request.getResponse().getContentAsString(), JwtResponse.class).getToken();
		});
		Product product = new Product();
		product.setName("Flour");
		product.setPrice((long) 2000);
		mock.perform(post("/products/unexists").content(mapper.writeValueAsString(product))
				.contentType("application/json")).andExpect(status().is4xxClientError());
	}

	@Test
	public void shouldBeAddAProductInStore() throws Exception{
		createRoles();
		User user = new User("shop6@mail.com","shop6","shops6","CR 1RA","111112");
		ArrayList<Role> rolesUser = new ArrayList<>();
		rolesUser.add(roles[1]);
		user.setAuthorities(rolesUser);
		JwtRequest req = new JwtRequest("shop6@mail.com","shops6");
		mock.perform(post("/register").content(mapper.writeValueAsString(user))
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		mock.perform(post("/login").content(mapper.writeValueAsString(req))
				.header("Content-Type","application/json")).andDo((request)->{
			token = mapper.readValue(request.getResponse().getContentAsString(), JwtResponse.class).getToken();
		});
		Shop shop = new Shop("shop6",new ArrayList<Product>(),"Av Pradilla Chia","generictype");
		mock.perform(post("/shops").header("Authorization",token).content(mapper.writeValueAsString(shop)).contentType("application/json"))
				.andExpect(status().is2xxSuccessful());
		Product product = new Product();
		product.setName("Flour");
		product.setPrice((long) 2000);
		String shopId = shopRepository.findByName("shop6").get().getId();
		mock.perform(post("/products/"+shopId).header("Authorization",token).contentType("application/json")
				.content(mapper.writeValueAsString(product))).andExpect(status().is2xxSuccessful());
	}

	@Test
	public void shouldBeGetProductsINShop() throws Exception{
		createRoles();
		User user = new User("shop7@mail.com","shop7","shops7","CR 1RA","111112");
		ArrayList<Role> rolesUser = new ArrayList<>();
		rolesUser.add(roles[1]);
		user.setAuthorities(rolesUser);
		JwtRequest req = new JwtRequest("shop7@mail.com","shops7");
		mock.perform(post("/register").content(mapper.writeValueAsString(user))
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		mock.perform(post("/login").content(mapper.writeValueAsString(req))
				.header("Content-Type","application/json")).andDo((request)->{
			token = mapper.readValue(request.getResponse().getContentAsString(), JwtResponse.class).getToken();
		});
		Shop shop = new Shop("shop7",new ArrayList<Product>(),"Av Pradilla Chia","generictype");
		mock.perform(post("/shops").header("Authorization",token).content(mapper.writeValueAsString(shop)).contentType("application/json"))
				.andExpect(status().is2xxSuccessful());
		Product product = new Product();
		product.setName("Pasta");
		product.setPrice((long) 2000);
		String shopId = shopRepository.findByName("shop7").get().getId();
		mock.perform(post("/products/"+shopId).header("Authorization",token).contentType("application/json")
				.content(mapper.writeValueAsString(product))).andExpect(status().is2xxSuccessful());
		mock.perform(get("/products/"+shopId)).andDo((response)->{
			Product[] products = mapper.readValue(response.getResponse().getContentAsString(),Product[].class);
			assertEquals(products[0].getName(),"Pasta");
		});
	}
}
