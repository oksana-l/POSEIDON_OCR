package com.nnk.springboot.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.LowercaseCharacterRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;

import lombok.SneakyThrows;

/**
 * This is the class for validate annotated fields.
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
	
	private String message;
	
    /**
     * Initialize.
     *
     * @param constraintAnnotation '@ValidPassword'
     */
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    	this.message = constraintAnnotation.message();
    }

    /**
     * Checks if the Password is valid.
     *
     * @param password String
     * @param context ConstraintValidatorContext
     * @return true, if is valid
     */
    @SneakyThrows
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
         
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
        		
        	// length between 8 and 50 characters
            new LengthRule(8, 50),
            
            // at least one upper-case character
            new UppercaseCharacterRule(1),
            
            // at least one lower-case character
            new LowercaseCharacterRule(1),
            
            // at least one digit character
            new DigitCharacterRule(1),
            
            // at least one symbol (special character)
            new SpecialCharacterRule(1),
            
            // no whitespace
            new WhitespaceRule()));
        
        RuleResult result = validator.validate(new PasswordData(password));
        
        if (result.isValid()) {
            return true;
        }        
        return false;
    }
}
