package thomas.isima.tp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Tp2ApplicationTests {

	@Autowired
	TravelService tfs;

	@Test
	public void negativeInput() throws Exception {
		//test sur les entées négatives
		//Assertions.assertEquals(null,tfs.feeCalculator(-10f),"Error message");
		//Assertions.assertEquals(null,tfs.feeCalculator(-10.00f),"Error message");
	}

	//
	@Test
	public void testLimit() throws Exception {
		//test sur les bornes
		//Assertions.assertEquals(0f,tfs.feeCalculator(0.00f),"Error message");
		Assertions.assertEquals(15.00f,tfs.feeCalculator(10.00f),"Error message");
		Assertions.assertEquals(26.80f,tfs.feeCalculator(39.5f),"Error message");
		Assertions.assertEquals(27.00f,tfs.feeCalculator(40.00f),"Error message");
		Assertions.assertEquals(38.00f,tfs.feeCalculator(60.00f),"Error message");
		Assertions.assertEquals(44.81f,tfs.feeCalculator(80.00f),"Error message");
	}

	@Test
	public void testInterval() throws Exception {
		//test avec valeurs dans les intervales
		Assertions.assertEquals(0.75f,tfs.feeCalculator(0.50f),"Error message");
		Assertions.assertEquals(17.81f,tfs.feeCalculator(17.02123f),"Error message");
		Assertions.assertEquals(31.40f,tfs.feeCalculator(48.00f),"Error message");
		Assertions.assertEquals(38.00f,tfs.feeCalculator(66.123f),"Error message");
		Assertions.assertEquals(44.81f,tfs.feeCalculator(86.896f),"Error message");
	}

	@Test
	public void testExtra() throws Exception {
		//test pour les valeurs au dessus de 60 km
		//Assertions.assertEquals(0f,tfs.extraRange(0f),"Error message");
		//Assertions.assertEquals(0f,tfs.extraRange(-10f),"Error message");
		//Assertions.assertEquals(0f,tfs.extraRange(-10.5f),"Error message");
		Assertions.assertEquals(0f,tfs.extraRange(60.00f),"Error message");
		Assertions.assertEquals(0f,tfs.extraRange(19.9f),"Error message");
		//Par lot de 20km
		Assertions.assertEquals(6.81f,tfs.extraRange(80.00f),"Error message");
		Assertions.assertEquals(6.81f,tfs.extraRange(99.9f),"Error message");
		Assertions.assertEquals(13.62f,tfs.extraRange(100.0f),"Error message");
		Assertions.assertEquals(13.62f,tfs.extraRange(115.7f),"Error message");
	}

	@Test
	public void testFloat() throws Exception {
		Assertions.assertEquals(0.75f,tfs.inRange(0.5f),"Error message");
		Assertions.assertEquals(15.20f,tfs.inRange(10.5f),"Error message");
		Assertions.assertEquals(27.00f,tfs.inRange(40.00f),"Error message");
		Assertions.assertEquals(38.00f,tfs.inRange(60.00f),"Error message");
		Assertions.assertEquals(38.00f,tfs.inRange(80.00f),"Error message");
	}

	@Test
	public void formatFloat() throws Exception {
		//Assertions.assertEquals(0.0f,tfs.onlyTwoDigit(0f),"Error message");
		Assertions.assertEquals(0.5f,tfs.onlyTwoDigit(0.5f),"Error message");
		Assertions.assertEquals(0.75f,tfs.onlyTwoDigit(0.75f),"Error message");
		Assertions.assertEquals(0.75f,tfs.onlyTwoDigit(0.75456f),"Error message");
		Assertions.assertEquals(0.76f,tfs.onlyTwoDigit(0.75656f),"Error message");
		//Assertions.assertEquals(-0.76f,tfs.onlyTwoDigit(-0.75956f),"Error message");
	}


	@Test
	public void validation(){
		//tfs.feeCalculator(-10f);
		//tfs.feeCalculator(0f);
		tfs.feeCalculator(0.1f);
		tfs.feeCalculator(10.1f);
		tfs.feeCalculator(17.123f);
		tfs.feeCalculator(39.5f);
		tfs.feeCalculator(61f);
		tfs.feeCalculator(81f);
		tfs.feeCalculator(99f);
	}

}
