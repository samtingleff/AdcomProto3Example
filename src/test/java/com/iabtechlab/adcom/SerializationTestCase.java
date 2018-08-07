package com.iabtechlab.adcom;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.iabtechlab.adcom.types.Adcom;
import com.iabtechlab.adcom.types.Adcom.CategoryTaxonomy;
import com.iabtechlab.adcom.types.Exchangea.ExchangeAExtension;
import com.iabtechlab.adcom.types.Exchangeb.ExchangeBExtension;

@RunWith(JUnit4.class)
public class SerializationTestCase {

	@Test
	public void testSeparateExtensions() throws InvalidProtocolBufferException {
		Adcom.Ad exchangeAObject1 = createExchangeAObject();
		Adcom.Ad exchangeBObject1 = createExchangeBObject();

		byte[] bytesForExchangeA = exchangeAObject1.toByteArray();
		byte[] bytesForExchangeB = exchangeBObject1.toByteArray();

		Adcom.Ad exchangeAObject2 = Adcom.Ad.parseFrom(bytesForExchangeA);
		ExchangeAExtension ext1 = exchangeAObject2.getExt().unpack(ExchangeAExtension.class);
		Assert.assertEquals(exchangeAObject1.getExt().unpack(ExchangeAExtension.class), ext1);

		Adcom.Ad exchangeBObject2 = Adcom.Ad.parseFrom(bytesForExchangeB);
		ExchangeBExtension ext2 = exchangeBObject2.getExt().unpack(ExchangeBExtension.class);
		Assert.assertEquals(exchangeBObject1.getExt().unpack(ExchangeBExtension.class), ext2);
	}

	private Adcom.Ad createExchangeAObject() {
		ExchangeAExtension exchangeAExtension = ExchangeAExtension.newBuilder()
				.setId("id-2")
				.setFoo("bar")
				.build();
		Adcom.Ad exchangeAObject = Adcom.Ad.newBuilder()
				.setId("some-other-id-here")
				.setCattax(CategoryTaxonomy.UNKNOWN_CATEGORY_TAXONOMY)
				.setExt(Any.pack(exchangeAExtension))
				.build();
		return exchangeAObject;
	}

	private Adcom.Ad createExchangeBObject() {
		ExchangeBExtension exchangeBExtension = ExchangeBExtension.newBuilder()
				.setId("id-3")
				.setBar(93294)
				.build();
		Adcom.Ad exchangeBObject = Adcom.Ad.newBuilder()
				.setId("some-other-id-here")
				.setCattax(CategoryTaxonomy.UNKNOWN_CATEGORY_TAXONOMY)
				.setExt(Any.pack(exchangeBExtension))
				.build();
		return exchangeBObject;
	}
}
