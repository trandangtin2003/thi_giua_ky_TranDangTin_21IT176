package com.bersyte.noteapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bersyte.noteapp.databinding.FragmentUpdateNoteBinding
import com.bersyte.noteapp.model.Note
import com.bersyte.noteapp.viewmodel.NoteViewModel


class TaskDetailFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private val args: TaskDetailFragmentArgs by navArgs()
    private lateinit var currentNote: Note
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.etNoteBodyUpdate.setText(currentNote.noteBody)
        binding.etnoteTitleUpdate.setText(currentNote.noteTitle)

        binding.fabDone.setOnClickListener {
            val noteTitle = binding.etnoteTitleUpdate.text.toString().trim()
            val body = binding.etNoteBodyUpdate.text.toString().trim()

            if (noteTitle.isNotEmpty()) {
                val note = Note(currentNote.id, noteTitle, body)
                noteViewModel.updateNote(note)

                view.findNavController().navigate(R.id.action_TaskDetailFragment_to_TaskListFragment)

            } else {
                activity?.toast("Enter a note noteTitle please")
            }
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {

            setPositiveButton("DELETE") { _, _ ->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(
                    R.id.action_TaskDetailFragment_to_TaskListFragment
                )
            }
            setNegativeButton("CANCEL", null)
        }.create().show()

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteNote()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}