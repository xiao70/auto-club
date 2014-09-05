package com.sinelead.car.club.constant;

import com.sinelead.car.club.R;

public class Constant
{
	// Btn�ı�ʶ
	public static final int BTN_FLAG_MAIN = 0x01;
	public static final int BTN_FLAG_MSG = 0x01 << 1;
	public static final int BTN_FLAG_MYCAR = 0x01 << 2;
	public static final int BTN_FLAG_TOOLS = 0x01 << 3;
	public static final int BTN_FLAG_SETTING = 0x01 << 4;

	// Fragment�ı�ʶ
	public static final String FRAGMENT_FLAG_MAIN = "��ҳ";
	public static final String FRAGMENT_FLAG_MSG = "��Ϣ";
	public static final String FRAGMENT_FLAG_MYCAR = "�ҵİ���";
	public static final String FRAGMENT_FLAG_TOOLS = "С����";
	public static final String FRAGMENT_FLAG_SETTING = "����";

	public static final String[] MAIN_TITLES = { "  ����  ", "����λ��", "����",
			"���Ѷ�̬", "�Ժ�����", "�Ź���", "������װ" };

	public static final String[] MAP_TITLES = { "��", "������", "����" };
	public static final String[] MAP_DETAILS = { "������ҵ���������˳·�ĳ���",
			"������û�а׸�����߸�˧������һ��", "����·!?��ɶ,������!" };
	public static final int[] MAP_IMAGES = { R.drawable.map_passanger,
			R.drawable.map_ride, R.drawable.map_navigation };

	public static final int UNKNOWN_REQUEST = -1;
	public static final int SEARCH_REQUEST = 0;

	public static final int RESULT_SEARCH = 2; // ��2��ʼ
}