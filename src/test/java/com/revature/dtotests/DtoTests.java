package com.revature.dtotests;

import org.junit.jupiter.api.Test;

import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.BusinessKeyMustExistRule;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.BusinessIdentityTester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

public class DtoTests {
	private String packageName = "com.revature.dtos";
	
	@Test
	void validateSettersAndGetters() {
		Validator validator = ValidatorBuilder.create()
				.with(new SetterMustExistRule(),
					  new GetterMustExistRule(),
					  new BusinessKeyMustExistRule())
				.with(new SetterTester(),
					  new GetterTester(),
					  new BusinessIdentityTester())
				.build();
		validator.validate(packageName);
	}
}
