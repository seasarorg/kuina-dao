/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.kuina.dao.it.conditional.dao;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
public class TopLinkManyToOneOwnerDaoTest extends AbstractManyToOneOwnerDaoTest {

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _add2() throws Exception {
		super._add2();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _subtract() throws Exception {
		super._subtract();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _subtract2() throws Exception {
		super._subtract2();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _multiply2() throws Exception {
		super._multiply2();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _divide2() throws Exception {
		super._divide2();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _like2() throws Exception {
		super._like2();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _isEmpty() throws Exception {
		super._isEmpty();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _trim() throws Exception {
		super._trim();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _trim2() throws Exception {
		super._trim2();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _trim_leading() throws Exception {
		super._trim_leading();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _trim_leading2() throws Exception {
		super._trim_leading2();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _trim_trailing() throws Exception {
		super._trim_trailing();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _trim_trailing2() throws Exception {
		super._trim_leading2();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _trim_both() throws Exception {
		super._trim_both();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _trim_both2() throws Exception {
		super._trim_both2();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _currentDate() throws Exception {
		super._currentDate();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _currentTime() throws Exception {
		super._currentTime();
	}

	@Override
	@Ignore("TopLinkが対応していないため")
	public void _currentTimestamp() throws Exception {
		super._currentTimestamp();
	}
}
