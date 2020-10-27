package edu.eci.ieti.ProjectIeti;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.ieti.ProjectIeti.model.*;
import edu.eci.ieti.ProjectIeti.persistence.RoleRepository;
import edu.eci.ieti.ProjectIeti.persistence.ShopRepository;
import edu.eci.ieti.ProjectIeti.persistence.UserRepository;
import edu.eci.ieti.ProjectIeti.security.JwtRequest;
import edu.eci.ieti.ProjectIeti.security.JwtResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
		"spring.data.mongodb.uri=mongodb://localhost:27017/test?&retryWrites=true&w=majority",
		"spring.data.mongodb.database=test"
})
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

	private Role[] roles;

	private String token;

	public void createRoles(){
		if(roles==null) {
			Role role1 = new Role();
			role1.setRole(ERole.ROLE_USER);
			role1 = roleRepository.save(role1);
			Role role2 = new Role();
			role2.setRole(ERole.ROLE_TENDERO);
			role2 = roleRepository.save(role2);
			Role role3 = new Role();
			role3.setRole(ERole.ROLE_ADMIN);
			role3 = roleRepository.save(role3);
			roles = new Role[]{role1, role2, role3};
			System.out.println("Creo Roles");
		}

	}

	@Test
	public void shoulBeAddANewUser() throws Exception{
		User user = new User("juan@mail.com","juan","pwd");
		mock.perform(post("/register").content(mapper.writeValueAsString(user))
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
	}

	@Test
	public void shouldntBeAddedUsersWithTheSameEmail() throws Exception{
		User user = new User("cam@mail.com","cam","cam");
		mock.perform(post("/register").content(mapper.writeValueAsString(user))
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		User user2 = new User("cam@mail.com","C cam","12345");
		mock.perform(post("/register").content(mapper.writeValueAsString(user))
				.header("Content-Type","application/json")).andExpect(status().is4xxClientError());
	}

	@Test
	public void shouldBeGetTheRole() throws Exception{
		createRoles();
		User user = new User("daniel@mail.com","daniel","daniel");
		ArrayList<Role> rolesUser = new ArrayList<>();
		rolesUser.add(roles[0]);
		user.setAuthorities(rolesUser);
		mock.perform(post("/register").content(mapper.writeValueAsString(user))
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		JwtRequest req = new JwtRequest("daniel@mail.com","daniel");
		user = userRepository.getUserByEmail("daniel@mail.com").get();
		userRepository.save(user);
		mock.perform(post("/login").content(mapper.writeValueAsString(req))
				.header("Content-Type","application/json")).andDo((request)->{
					token = mapper.readValue(request.getResponse().getContentAsString(), JwtResponse.class).getToken();
		});
		mock.perform(get("/role").header("Authorization",token)).andDo((request)->{
			String[] rol = mapper.readValue(request.getResponse().getContentAsString(),String[].class);
			assertEquals(rol[0],"ROLE_USER");
		});
	}

	@Test
	public void shouldBeGiveTheUserName() throws Exception{
		User user = new User("charlie@mail.com","Charlie","daniel");
		JwtRequest req = new JwtRequest("charlie@mail.com","daniel");
		mock.perform(post("/register").content(mapper.writeValueAsString(user))
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
		User user = new User("shop1@mail.com","shop","shops");
		JwtRequest req = new JwtRequest("shop1@mail.com","shops");
		mock.perform(post("/register").content(mapper.writeValueAsString(user))
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
		User user = new User("shop2@mail.com","shop2","shops2");
		JwtRequest req = new JwtRequest("shop2@mail.com","shops2");
		mock.perform(post("/register").content(mapper.writeValueAsString(user))
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		mock.perform(post("/login").content(mapper.writeValueAsString(req))
				.header("Content-Type","application/json")).andDo((request)->{
			token = mapper.readValue(request.getResponse().getContentAsString(), JwtResponse.class).getToken();
		});
		Shop shop = new Shop("Shop2",new ArrayList<Product>(),"Calle 10ma Bogota DC","Supermarket");
		mock.perform(post("/shops").header("Authorization",token).content(mapper.writeValueAsString(shop)).contentType("application/json"))
				.andExpect(status().is2xxSuccessful());
		Shop shop2 = new Shop("Shop3",new ArrayList<Product>(),"Calle 10ma Bogota DC","Restaurant");
		mock.perform(post("/shops").header("Authorization",token).content(mapper.writeValueAsString(shop)).contentType("application/json"))
				.andExpect(status().is4xxClientError());
	}
	@Test
	public void shouldBeQueryAShop() throws Exception{
		User user = new User("shop3@mail.com","shop3","shops3");
		JwtRequest req = new JwtRequest("shop3@mail.com","shops3");
		mock.perform(post("/register").content(mapper.writeValueAsString(user))
				.header("Content-Type","application/json")).andExpect(status().is2xxSuccessful());
		mock.perform(post("/login").content(mapper.writeValueAsString(req))
				.header("Content-Type","application/json")).andDo((request)->{
			token = mapper.readValue(request.getResponse().getContentAsString(), JwtResponse.class).getToken();
		});
		Shop shop = new Shop("shop4",new ArrayList<Product>(),"Calle 5ta Bogota DC","Supermarket");
		mock.perform(post("/shops").header("Authorization",token).content(mapper.writeValueAsString(shop)).contentType("application/json"))
				.andExpect(status().is2xxSuccessful());
		mock.perform(get("/shops/shop4")).andExpect(status().is2xxSuccessful());
	}

	@Test
	public void shouldntBeQueryUnexistShop() throws Exception{
		mock.perform(get("/shops/unexistShop")).andExpect(status().is4xxClientError());
	}

	@Test
	public void shouldBeFindAshopByType() throws Exception{
		User user = new User("shoptype@mail.com","shoptype","shopstype");
		JwtRequest req = new JwtRequest("shoptype@mail.com","shopstype");
		mock.perform(post("/register").content(mapper.writeValueAsString(user))
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
		User user = new User("shop5@mail.com","shop5","shops5");
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
		User user = new User("shop6@mail.com","shop6","shops6");
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
		User user = new User("shop7@mail.com","shop7","shops7");
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
