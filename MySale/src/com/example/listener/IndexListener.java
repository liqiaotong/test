package com.example.listener;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.example.entity.Game;

public class IndexListener extends DefaultHandler{
	
	public List<Game> list=new ArrayList<Game>();
	public Game game;
	public String title;
	
	public IndexListener(List<Game> lt) {
		super();
		this.list = lt;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
		String context=new String(ch,start,length);
		if("name".equals(title))
		{
			game.setName(context);
		}
		if("image".equals(title))
		{
			game.setImage(context);
		}
		if("money".equals(title))
		{
			game.setMoney(context);
		}
		if("info".equals(title))
		{
			game.setInfo(context);
		}
		super.characters(ch, start, length);
	}

	
	

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		title=localName;
		if("game".equals(localName))
		{
			game=new Game();
		}
		super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		
		if("game".equals(localName))
		{
			list.add(game);
		}
		title="";
		super.endElement(uri, localName, qName);
		
	}

}
