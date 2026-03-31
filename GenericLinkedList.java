	import java.util.ListIterator;
	import java.util.NoSuchElementException;
	
	public class GenericLinkedList<T> implements Iterable<T> {
		private Node<T> head;
		private Node<T> tail;
		private int size = 0;
	
		public GenericLinkedList() {
			head = null;
			tail = null;
		}
	
		public boolean contains(T element) {
			GenericIterator<T> it = iterator();
			while (it.hasNext()) {
				if (it.next().equals(element))
					return true;
			}
			return false;
		}
	
		public T get(int index) {
			GenericIterator<T> it = iterator();
			int i = 0;
			while (it.hasNext()) {
				if (i == index)
					return it.next();
				it.next();
				i++;
			}
			throw new IndexOutOfBoundsException();
		}
	
		public T getFirst() {
			return head.getData();
		}
	
		public T getLast() {
			return tail.getData();
		}
	
		public void addFirst(T t) {
			Node<T> val = new Node<T>(t, head, null);
			System.out.println("size " + size);
			if (size == 0) {
				head = tail = val;
			} else {
				head.setPrevNode(val);
				head = val;
			}
			size++;
	
		}
	
		public void addLast(T t) {
			Node<T> val = new Node<T>(t, null, tail);
			if (size == 0) {
				head = tail = val;
			} else {
				tail.setNextNode(val);
				tail = val;
			}
			size++;
		}
	
		public boolean remove(T element) {
			for (ListIterator<T> it = this.iterator(); it.hasNext(); ) {
			    T t = it.next();
				if (t.equals(element)) { //
					it.remove();
					if (head.getData().equals(element))
					{
						head.getNextNode().setPrevNode(null);
						head = head.getNextNode();
					}
					if (tail.getData().equals(element))
					{
						tail.getPrevNode().setNextNode(null);
						tail = tail.getPrevNode();
					}
					size--;
					return true;
				}
			}
			return false;
		}
	
		public T removeFirst() {
			GenericIterator<T> it = iterator();
			if (!it.hasNext() || !it.hasPrevious())
				throw new NoSuchElementException("That element does not exist.");
			T val = it.next();
			it.remove();
			size--;
			return val;
		}
	
		public T removeLast() throws NoSuchElementException {
			GenericIterator<T> it = iterator();
			if (!it.hasNext() || !it.hasPrevious())
				throw new NoSuchElementException("That element does not exist.");
			T val = it.previous();
			it.remove();
			size--;
			return val;
		}
	
		public int size() {
			return size;
		}
	
		public boolean isEmpty() {
			return (size == 0);
		}
	
		public Object[] toArray() {
			Object[] arr = new Object[size()];
			int i = 0;
			Node<T> curr = head;
			while (curr != null) {
				arr[i] = curr.getData();
				curr = curr.getNextNode();
				i++;
			}
	
			return arr;
		}
	
		public void clear() {
			head = null;
			tail = null;
		}
	
		public GenericIterator<T> iterator() {
			return new GenericIterator<>(this.head, this.tail);
		}
	
	}
	
	class Node<U> {
		private U data;
		private Node<U> next;
		private Node<U> prev;
	
		protected Node(U data, Node<U> next, Node<U> prev) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
	
		protected Node() {
			this(null, null, null);
		}
	
		protected U getData() {
			return data;
		}
	
		protected void setData(U data) {
			this.data = data;
		}
	
		protected Node<U> getNextNode() {
			return next;
		}
	
		protected void setNextNode(Node<U> next) {
			this.next = next;
		}
	
		protected void setPrevNode(Node<U> prev) {
			this.prev = prev;
		}
	
		protected Node<U> getPrevNode() {
			return prev;
		}
	
	}
	
	class GenericIterator<U> implements ListIterator<U> {
		private Node<U> current;
		private Node<U> head;
		private Node<U> tail;
		private boolean wasNextCalled;
		private boolean wasPrevCalled;
		private boolean isTail;
		private boolean isHead;
	
		public GenericIterator(Node<U> head, Node<U> tail) {
			current = head;
			this.head = head;
			this.tail = tail;
			wasNextCalled = false;
			wasPrevCalled = false;
			isTail = false;
			isHead = false;
		}
	
		@Override
		public boolean hasNext() {
			return current != null;
		}
	
		@Override
		public U next() {
			if (hasNext()) {
				wasNextCalled = true;
				U val = current.getData();
				current = current.getNextNode();
				isTail = (current == null);
				return val;
			} else {
				throw new NoSuchElementException(); // see if can do message
			}
	
		}
	
		@Override
		public boolean hasPrevious() {
			return current != null;
		}
	
		@Override
		public U previous() {
			if (hasPrevious()) {
				wasPrevCalled = true;
				U val = current.getData();
				current = current.getPrevNode();
				isHead = (current == null);
				return val;
			} else
				throw new NoSuchElementException("That element does not exist."); // fix
		}
	
		@Override
		public void remove() {
			Node<U> toRemove = null;
			if (isTail)
				toRemove = tail;
			else if (isHead)
				toRemove = head;
			else if (wasNextCalled) {
				toRemove = current.getPrevNode();
				wasNextCalled = false;
			} else if (wasPrevCalled) {
				toRemove = current.getNextNode();
				wasPrevCalled = false;
			} else
				throw new IllegalStateException();
			if (toRemove == head)
				head = toRemove.getNextNode();
			else
				toRemove.getPrevNode().setNextNode(toRemove.getNextNode());
			if (toRemove == tail)
				tail = toRemove.getPrevNode();
			else
				toRemove.getNextNode().setPrevNode(toRemove.getPrevNode());
		}
	
		public Node<U> getCurrent() {
			return current;
		}
	
		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}
	
		@Override
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}
	
		@Override
		public void set(Object e) {
			throw new UnsupportedOperationException();
	
		}
	
		@Override
		public void add(Object e) {
			throw new UnsupportedOperationException();
	
		}
	}