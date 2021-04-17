package info.jab.microservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {

	@NonNull
	@NotEmpty
	@Pattern(message="max 10 words please" , regexp="^[a-zA-Z-.0-9]{1,15}$")
	private String username;

	@NotNull
	@NotEmpty
	@Pattern(message="max 10 words please" , regexp="^[a-zA-Z-.0-9]{1,15}$")
	private String password;
}