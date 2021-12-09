package tes.test1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tes.Calculator;

class CalculatorTest {

	@Test
	void testSum() {
		Calculator cal=new Calculator();
		assertEquals(30, cal.sum(10, 20));
		
	}

}
