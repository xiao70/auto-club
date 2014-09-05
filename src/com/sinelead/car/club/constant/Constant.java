
/*
*
*	��Ȩ����(C) 2014 xiao70 <196245957@qq.com>
*   
*
*	������Ϊ������������������������������������GNUͨ�ù�����Ȩ����Ա������ٴη������޸ģ����������ݵ��Ǳ���Ȩ�ĵ����棬������ѡ�ģ���һ�պ��еİ汾��
*	�������ǻ���ʹ��Ŀ�Ķ����Է�����Ȼ�������κε������Σ����޶������Ի��ض�Ŀ����������Ϊ��Ĭʾ�Ե��������������GNUͨ�ù�����Ȩ��
*	��Ӧ���յ������ڱ������GNUͨ�ù�����Ȩ�ĸ��������û�У������
*	<http://www.gnu.org/licenses/>.
* 	�������Ŀ���ñ��ļ��������Ŀ���룬�뱣�����ߡ���Ȩ�����֪ͨ�����������Ϊ��ҵĿ�����ڳ��򽻻����棬������Ϣ�б������ߣ���Ȩ����Ϣ��
*
*	This program is free software; you can redistribute it and/or modify
*	it under the terms of the GNU General Public License as published by
*	the Free Software Foundation; either version 3 of the License, or
*	(at your option) any later version.
*      
*	This program is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*	GNU General Public License for more details.
*      
*	You should have received a copy of the GNU General Public License
*	along with this program; if not, write to the Free Software
*	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
*	MA 02110-1301, USA.
*/

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