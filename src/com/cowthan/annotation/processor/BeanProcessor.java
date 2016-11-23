package com.cowthan.annotation.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

public class BeanProcessor extends AbstractProcessor {

	// 元素操作的辅助类
	Elements elementUtils;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		// 元素操作的辅助类
		elementUtils = processingEnv.getElementUtils();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {

		// 获得被该注解声明的元素
		Set<? extends Element> elememts = roundEnv
				.getElementsAnnotatedWith(Seriable.class);
		TypeElement classElement = null;// 声明类元素
		List<VariableElement> fields = null;// 声明一个存放成员变量的列表
		// 存放二者
		Map<String, List<VariableElement>> maps = new HashMap<String, List<VariableElement>>();
		// 遍历
		for (Element ele : elememts) {
			System.out.println(ele.getSimpleName());
		}

		return true;
	}

}
